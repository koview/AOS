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
    val writer: String,
    val profileImage: CoviewImage?,
    val imageList: List<CoviewImage?>,
    val purchaseLinkList: List<CoviewPurchaseLink>,
    val totalCommentCount: Long,
    val totalLikesCount: Long,
    val createdAt: String,
    val updatedAt: String
)

data class CoviewImage(
    val imageId: Long,
    val url: String
)

data class CoviewPurchaseLink(
    val purchaseLinkId: Long,
    val productId: Long,
    val purchaseUrl: String,
    val shopName: String,
    val verifiedType: VerifiedType
)
