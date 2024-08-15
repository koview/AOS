package com.example.koview.data.model.response

import com.example.koview.presentation.ui.main.home.model.Category
import java.io.Serializable

data class ProductsResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: ProductsResult
) : Serializable

data class ProductsResult(
    val productList: List<SingleProduct> = emptyList(),
    val getNumber: Int = 0,
    val hasPrevious: Boolean = false,
    val hasNext: Boolean = false
) : Serializable

data class SingleProduct(
    val productId: Long,
    val productName: String,
    val category: Category?,
    val createdDate: String,
    val restrictedDate: String?,
    val reviewCount: Long,
    val status: Status,
    val productImageUrls: List<ProductImageUrls>?,
    val purchaseLinkList: List<PurchaseLinkList>
) : Serializable

data class ProductImageUrls(
    val imageId: Long,
    val url: String
) : Serializable

data class PurchaseLinkList(
    val purchaseLinkId: Long,
    val productId: Long,
    val purchaseUrl: String,
    val shopName: String,
    val verifiedType: VerifiedType
) : Serializable

enum class Status() {
    NORMAL, FAMOUS, RESTRICTED
}

enum class VerifiedType() {
    UNVERIFIED, DISCONTINUED, VERIFIED
}

