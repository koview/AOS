package com.example.koview.data.config

import android.util.Log
import com.example.koview.BuildConfig
import com.example.koview.app.App.Companion.sharedPreferences
import com.example.koview.data.model.BaseState
import com.example.koview.data.model.requeset.ReIssueRequest
import com.example.koview.data.model.response.ReIssueResponse
import com.example.koview.data.model.runRemote
import com.example.koview.data.remote.AuthApi
import com.example.koview.presentation.utils.Constants.ACCESS_TOKEN
import com.example.koview.presentation.utils.Constants.REFRESH_TOKEN
import com.example.koview.presentation.utils.Constants.TAG
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class BearerInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val response = chain.proceed(originalRequest)

        var newAccessToken: String? = null

        // API 통신중 특정코드 에러 발생 (accessToken 만료)
        if (response.code == 410) {

            runBlocking {

                val accessToken = sharedPreferences.getString(ACCESS_TOKEN, null)
                val refreshToken = sharedPreferences.getString(REFRESH_TOKEN, null)

                if (!accessToken.isNullOrEmpty() && !refreshToken.isNullOrEmpty()) {

                    val request = ReIssueRequest(accessToken, refreshToken)

                    getNewAccessToken(request).let {
                        when (it) {
                            is BaseState.Success -> {
                                // token 저장
                                sharedPreferences.edit()
                                .putString(ACCESS_TOKEN, it.body.result.accessToken)
                                .putString(REFRESH_TOKEN, it.body.result.refreshToken)
                                .apply()

                                newAccessToken = it.body.result.accessToken
                            }

                            is BaseState.Error -> {
                                // token 삭제
                                sharedPreferences.edit()
                                    .remove(ACCESS_TOKEN)
                                    .remove(REFRESH_TOKEN)
                                    .apply()
                                Log.d(TAG, it.msg)
                            }
                        }
                    }
                }
            }

            newAccessToken?.let {
                val newRequest = originalRequest.newBuilder()
                    .addHeader("Authorization", "Bearer $it")
                    .build()
                return chain.proceed(newRequest)
            }
        }

        return response
    }

    private suspend fun getNewAccessToken(tokenList: ReIssueRequest): BaseState<ReIssueResponse> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_DEV_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val api = retrofit.create(AuthApi::class.java)
        return runRemote {
            api.refreshToken(tokenList)
        }
    }
}