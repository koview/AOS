package com.example.koview.data.model.response

import java.io.Serializable

data class QueryResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: QueryResult
): Serializable

data class QueryResult(
    val queryList: List<QueryResultList>,
    val getNumber: Long,
    val hasPrevious: Boolean,
    val hasNext: Boolean
): Serializable

data class QueryResultList(
    val queryId: Long,
    val content: String,
    val writer: String,
    val profileImage: QueryImage,
    val imageList: List<QueryImage>,
    val totalWithQueryCount: Long,
    val isWithQuery: Boolean,
    val totalViewCount: Long,
    val totalAnswerCount: Long,
    val purchaseLinkList: List<QueryPurchaseLinkList>,
    val createdAt: String,
    val updatedAt: String
): Serializable

data class QueryImage(
    val imageId: Long,
    val url: String
): Serializable

data class QueryPurchaseLinkList(
    val purchaseLinkId: Long,
    val productId: Long,
    val purchaseUrl: String,
    val shopName: String,
    val verifiedType: VerifiedType
): Serializable