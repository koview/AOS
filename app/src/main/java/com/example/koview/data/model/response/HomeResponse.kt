package com.example.koview.data.model.response

data class HomeResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: HomeResult
)

data class HomeResult(
    val famousFourProducts: List<ProductInfo>,
    val restrictedFourProducts: List<ProductInfo>,
    val lastFourQueries: List<HomeQueryList>
)

data class ProductInfo(
    val productName: String,
    val image: ImageInfo
)

data class HomeQueryList(
    val queryId: Long,
    val content: String,
    val writer: String,
    val profileImage: ImageInfo?,
    val imageList: List<ImageInfo>?,
    val totalWithQueryCount: Long,
    val isWithQuery: Boolean,
    val totalViewCount: Long,
    val totalAnswerCount: Long,
    val purchaseLinkList: List<PurchaseLinkList>?,
    val createdAt: String,
    val updatedAt: String
)

data class ImageInfo(
    val imageId: Long,
    val url: String
)