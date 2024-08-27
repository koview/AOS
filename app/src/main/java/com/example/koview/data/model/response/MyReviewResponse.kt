package com.example.koview.data.model.response

// 내 정보 조회
data class GetMyDetailResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: ProfileResponseDTO
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
    val profileInfo: ProfileResponseDTO,
    val imageList: List<ImageDTO>,
    val totalCommentCount: Long,
    val totalLikesCount: Long,
    val createdAt: String,
    val updatedAt: String
)

// 마이페이지 리뷰 화면을 위한 데이터 클래스
data class MyReview(
    val memberId: Long,
    val reviewId: Long,
    val content: String,
    val writer: String,
    val imageList: List<ImageDTO>,
    val totalCommentCount: Long,
    val totalLikesCount: Long,
    val createdAt: String,
    val updatedAt: String,
    var isSelected: Boolean = false
)

// 리뷰 프로필 정보
data class ProfileResponseDTO(
    val imageId: Long,
    val imageUrl: String,
    val memberId: Long,
    val memberNickname: String
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




