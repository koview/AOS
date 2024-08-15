package com.example.koview.data.model.response

data class CreateQueryResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: QueryPostResult,
)

data class QueryPostResult(
    val queryId: Long,
    val content: String,
    val writer: String,
    val profileImage: ImageDTO?,
    val imageList: List<ImageDTO>?,
    val totalWithQueriesCount: Long,
    val isWithQuery: Boolean,
    val totalViewCount: Long,
    val createdAt: String,
    val updatedAt: String,
)