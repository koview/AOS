package com.example.koview.data.model.response

data class ReviewLikeResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: LikeInfo,
)

data class LikeInfo(
    val likeId: Long,
)