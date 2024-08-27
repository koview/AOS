package com.example.koview.presentation.ui.main.global.productdetail

import com.example.koview.data.model.response.ProductReviewDetail

interface ProductDetailInterface {
    fun onLikeClick(item: ProductReviewDetail)
    fun onContentClick(reviewId: Long, nickname: String, memberId: Long)
}