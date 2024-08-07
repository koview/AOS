package com.example.koview.data.model.response

data class ReIssueResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: ReIssueResult,
)

data class ReIssueResult(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String,
)
