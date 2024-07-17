package com.example.koview.data.model.response

data class SignUpResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: List<SignUpResult>
)

data class SignUpResult(
    val id: Long,
    val email: String,
    val nickname: String,
    val age: Int,
    val memberLikedShopList: List<LikedShop>
)

data class LikedShop(
    val shopId: Int,
    val shopName: String
)