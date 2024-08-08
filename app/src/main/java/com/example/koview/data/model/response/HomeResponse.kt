package com.example.koview.data.model.response

data class HomeResponse (
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: HomeResult
)

data class HomeResult (
    val famousFourProducts: List<ProductInfo>,
    val restrictedFourProducts: List<ProductInfo>,
)

data class ProductInfo(
    val productName: String,
    val image: ImageInfo
)

data class ImageInfo(
    val imageId: Long,
    val url: String
)