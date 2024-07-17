package com.example.koview.data.repository

import com.example.koview.data.model.BaseState
import com.example.koview.data.model.requeset.SignInRequest
import com.example.koview.data.model.requeset.SignUpRequest
import com.example.koview.data.model.response.SignInResponse
import com.example.koview.data.model.response.SignUpResponse
import com.example.koview.data.model.runRemote
import com.example.koview.data.remote.IntroApi
import javax.inject.Inject

class IntroRepositoryImpl @Inject constructor(private val api: IntroApi) : IntroRepository {

    override suspend fun memberSignUp(body: SignUpRequest): BaseState<SignUpResponse> =
        runRemote { api.memberSignUp(body) }

    override suspend fun memberSignIn(body: SignInRequest): BaseState<SignInResponse> =
        runRemote { api.memberSignIn(body) }
}