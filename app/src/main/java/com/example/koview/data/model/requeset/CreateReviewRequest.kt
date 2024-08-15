package com.example.koview.data.model.requeset

data class CreateReviewRequest (
    val content: String,
    val imagePathIdList: List<Long>,
    val purchaseLinkList: List<PurchaseLinkDTO>
)
data class PurchaseLinkDTO(
    val purchaseLink: String,
    val shopName: String
)

