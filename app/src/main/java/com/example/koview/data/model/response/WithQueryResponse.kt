package com.example.koview.data.model.response

data class WithQueryResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: WithQueryResult
)

data class WithQueryResult(
    val withQueryId: Long
)