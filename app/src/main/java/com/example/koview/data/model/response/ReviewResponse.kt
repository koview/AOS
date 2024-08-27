package com.example.koview.data.model.response

data class ReviewDetailResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: ReviewDetailResult
)

data class ReviewDetailResult(
    val reviewList: List<ReviewDetailList>,
    val getNumber: Long,
    val hasPrevious: Boolean,
    val hasNext: Boolean
)

data class ReviewDetailList(
    val reviewId: Long,
    val content: String,
    val profileInfo: ProfileResponseDTO,
    val imageList: List<ImageDTO?>,
    val purchaseLinkList: List<PurchaseLinkList>,
    val totalCommentCount: Long,
    val totalLikeCount: Long,
    val isLiked: Boolean,
    val createdAt: String,
    val updatedAt: String
)

data class ReviewImageResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: List<ImageDTO>
)

data class CreateReviewResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: ReviewResult
)
data class ReviewResult(
    val reviewId: Long,
    val content: String,
    val profileInfo: ProfileResponseDTO,
    val imageList: List<ImageDTO>,
    val totalCommentCount: Long,
    val totalLikesCount: Long,
    val isLiked: Boolean,
    val createdAt: String,
    val updatedAt: String
)