package com.example.koview.data.model.response

data class GetCoviewReviewsResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: ReviewsResult
)

data class ReviewsResult(
    val reviewList: List<CoviewReviewList>,
    val getNumber: Int,
    val hasPrevious: Boolean,
    val hasNext: Boolean
)

data class CoviewReviewList(
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
