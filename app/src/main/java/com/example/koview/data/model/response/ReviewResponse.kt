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
    val writer: String,
    val profileImage: ReviewDetailImage,
    val imageList: List<ReviewDetailImage>,
    val purchaseLinkList: List<PurchaseLinkList>,
    val totalCommentCount: Long,
    val totalLikesCount: Long,
    val createdAt: String,
    val updatedAt: String
)

data class ReviewDetailImage(
    val imageId: Long,
    val url: String
)

data class ReviewImageResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: List<ReviewDetailImage>
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
    val writer: String,
    val profileImage: ReviewDetailImage,
    val imageList: List<ReviewDetailImage>,
    val totalCommentCount: Long,
    val totalLikesCount: Long,
    val isLiked: Boolean,
    val createdAt: String,
    val updatedAt: String
)