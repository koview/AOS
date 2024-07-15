package com.example.koview.data.repository

import com.example.koview.data.model.BaseState
import com.example.koview.data.model.requeset.SignInRequest
import com.example.koview.data.model.requeset.SignUpRequest
import com.example.koview.data.model.response.SignInResponse
import com.example.koview.data.model.response.SignUpResponse

interface IntroRepository {

    suspend fun memberSignUp(
        body: SignUpRequest
    ): BaseState<SignUpResponse>

    suspend fun memberSignIn(
        body: SignInRequest
    ): BaseState<SignInResponse>
}