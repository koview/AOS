package com.example.koview.data.model.requeset

data class SignUpRequest(
    val email: String,
    val loginPw: String,
    val nickname: String,
    val age: Int,
    val shopName: Array<String>
)