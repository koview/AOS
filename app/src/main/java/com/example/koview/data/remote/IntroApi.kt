package com.example.koview.data.remote

import com.example.koview.data.model.requeset.SignInRequest
import com.example.koview.data.model.requeset.SignUpRequest
import com.example.koview.data.model.response.SignInResponse
import com.example.koview.data.model.response.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IntroApi {

    @POST("member/signup")
    suspend fun memberSignUp(
        @Body params: SignUpRequest
    ): Response<SignUpResponse>

    @POST("member/signin")
    suspend fun memberSignIn(
        @Body params: SignInRequest
    ): Response<SignInResponse>
}