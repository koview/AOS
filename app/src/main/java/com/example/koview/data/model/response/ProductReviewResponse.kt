package com.example.koview.data.model.response

import java.io.Serializable

data class ProductReviewResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: ProductReviewResult
): Serializable

data class ProductReviewResult (
    val detail: ProductReviewDetail,
    val reviewPaging: ProductReviewPaging
)

data class ProductReviewDetail (
    val answerId: Long,
    val content: String,
    val writer: String,
    val imageList: List<ProductReviewDetailImageList>,
    val totalLikeCount: Long,
    val isLiked: Boolean,
    val commentCount: Long,
    val createdAt: String,
    val updatedAt: String
)
data class ProductReviewPaging (
    val reviewList: List<ProductReviewDetail>,
    val getNumber: Long,
    val hasPrevious: Boolean,
    val hasNext: Boolean,
    val getTotalPages: Long,
    val getTotalElements: Long,
    val first: Boolean,
    val last: Boolean
)

data class ProductReviewDetailImageList (
    val imageId: Long,
    val url: String
)
