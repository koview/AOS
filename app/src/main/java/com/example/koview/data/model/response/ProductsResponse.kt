package com.example.koview.data.model.response

import com.example.koview.presentation.ui.main.home.model.Category

data class ProductsResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: ProductsResult
)

data class ProductsResult(
    val productList: List<SingleProduct>,
    val getNumber: Int,
    val hasPrevious: Boolean,
    val hasNext: Boolean
)

data class SingleProduct(
    val productId: Long,
    val productName: String,
    val category: Category?,
    val createdDate: String,
    val restrictedDate: String?,
    val reviewCount: Long,
    val status: Status,
    val productImageUrls: List<ProductImageUrls>?,
    val purchaseLinkList: List<PurchaseLinkList>?
)

data class ProductImageUrls(
    val imageId: Long,
    val url: String
)

data class PurchaseLinkList(
    val purchaseLinkId: Long,
    val productId: Long,
    val purchaseUrl: String,
    val shopName: String,
    val verifiedType: VerifiedType
)

enum class Status() {
    NORMAL, FAMOUS, RESTRICTED
}

enum class VerifiedType() {
    UNDEFINED, DEFINED
}

