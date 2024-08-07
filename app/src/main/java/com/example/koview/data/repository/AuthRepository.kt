package com.example.koview.data.repository

import com.example.koview.data.model.BaseState
import com.example.koview.data.model.requeset.ReIssueRequest
import com.example.koview.data.model.response.ReIssueResponse

interface AuthRepository {

    suspend fun refreshToken(
        body: ReIssueRequest
    ): BaseState<ReIssueResponse>
}