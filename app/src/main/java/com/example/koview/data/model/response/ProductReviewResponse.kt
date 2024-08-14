package com.example.koview.data.model.response

import java.io.Serializable

data class ProductReviewResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: ProductReviewResult
): Serializable

data class ProductReviewResult (
    val reviewList: List<ProductReviewDetail>,
    val getNumber: Long,
    val hasPrevious: Boolean,
    val hasNext: Boolean,
    val getTotalPages: Long,
    val getTotalElements: Long,
    val isFirst: Boolean,
    val isLast: Boolean
)

data class ProductReviewDetail (
    val reviewId: Long,
    val content: String,
    val writer: String,
    val profileImage: String?,
    val imageList: List<ProductReviewDetailImageList>,
    val totalCommentCount: Long,
    val totalLikesCount: Long,
    val isLiked: Boolean,
    val createdAt: String,
    val updatedAt: String
)

data class ProductReviewDetailImageList (
    val imageId: Long,
    val url: String
)
