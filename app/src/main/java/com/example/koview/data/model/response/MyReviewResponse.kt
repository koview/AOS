package com.example.koview.data.model.response

import java.time.LocalDate

// 내 정보 조회
data class GetMyDetailResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: MyDetailResult
)

data class MyDetailResult(
    val imageId: Long,
    val url: String,
    val nickname: String
)

// 내 리뷰 전체 조회
data class GetMyReviewsResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: GetMyReviewsResult
)

data class GetMyReviewsResult(
    val reviewList: List<ReviewList>,
    val getNumber: Int,
    val hasPrevious: Boolean,
    val hasNext: Boolean
)

data class ReviewList(
    val reviewId: Long,
    val content: String,
    val writer: String,
    val imageList: List<ImageDTO>,
    val totalCommentCount: Long,
    val totalLikesCount: Long,
    val createdAt: String,
    val updatedAt: String
)

// 이미지 DTO
data class ImageDTO(
    val imageId: Long,
    val url: String,
)



// 내 리뷰 상세 조회
data class GetMyReviewDetailResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: GetMyReviewDetailResult
)

data class GetMyReviewDetailResult(
    val reviewList: List<ReviewList>,
    val getNumber: Int,
    val hasPrevious: Boolean,
    val hasNext: Boolean
)



// 내 리뷰 리스트 삭제
data class DeleteMyReviewsResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String
)




