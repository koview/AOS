package com.example.koview.data.model.requeset

data class CreateQueryRequest(
    val content: String,
    val imagePathIdList: List<Long>,
    val purchaseLinkList: List<PurchaseLinkDTO>
)
