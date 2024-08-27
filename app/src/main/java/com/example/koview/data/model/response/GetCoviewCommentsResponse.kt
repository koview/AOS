package com.example.koview.data.model.response

data class GetCoviewCommentsResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: CoviewCommentResult,
)

data class CoviewCommentResult(
    val commentList: List<CoviewCommentItem>?,
    val getNumber: Int,
    val hasPrevious: Boolean,
    val hasNext: Boolean,
    val getTotalPages: Int,
    val getTotalElements: Int,
    val first: Boolean,
    val last: Boolean,
)

data class CoviewCommentItem(
    val commentId: Long,
    val content: String,
    val writer: String,
    val profileImage: ImageDTO,
    val createdDate: String,
)