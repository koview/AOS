package com.example.koview.data.remote

import com.example.koview.data.model.requeset.ReIssueRequest
import com.example.koview.data.model.response.ReIssueResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("member/reissue")
    suspend fun refreshToken(
        @Body params: ReIssueRequest
    ): Response<ReIssueResponse>

}