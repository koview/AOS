package com.example.koview.presentation.ui.main.global.product

import com.example.koview.data.model.response.SingleProduct

interface ProductInterface {
    fun onProductClick(product: SingleProduct)
    fun onProductShopTagClick(url: String)
}