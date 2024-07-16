package com.example.koview.data.config

import android.util.Log
import com.example.koview.app.App.Companion.sharedPreferences
import com.example.koview.presentation.utils.Constants.ACCESS_TOKEN
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class AccessTokenInterceptor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

        val jwt: String? = sharedPreferences.getString(ACCESS_TOKEN, null)
        Log.d("accessToken", jwt.toString())

        jwt?.let {
            builder.addHeader("Authorization", jwt)
        } ?: run {

        }

        return chain.proceed(builder.build())
    }
}
