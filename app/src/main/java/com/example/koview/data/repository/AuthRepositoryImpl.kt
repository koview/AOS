package com.example.koview.data.repository

import com.example.koview.data.model.BaseState
import com.example.koview.data.model.requeset.ReIssueRequest
import com.example.koview.data.model.response.ReIssueResponse
import com.example.koview.data.model.runRemote
import com.example.koview.data.remote.AuthApi
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val api: AuthApi) : AuthRepository {

    override suspend fun refreshToken(body: ReIssueRequest): BaseState<ReIssueResponse> =
        runRemote { api.refreshToken(body) }

}