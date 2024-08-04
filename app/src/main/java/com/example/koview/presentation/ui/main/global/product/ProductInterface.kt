package com.example.koview.presentation.ui.main.global.product

import com.example.koview.presentation.ui.main.global.product.model.Product

interface ProductInterface {
    fun onProductClick(product: Product)
    fun onProductShopTagClick(url: String)
}