package com.example.koview.data.model.response

data class SignInResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: SignInResult
)

data class SignInResult(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String
)