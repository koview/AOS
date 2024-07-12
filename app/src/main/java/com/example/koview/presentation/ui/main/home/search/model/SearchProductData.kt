package com.example.koview.presentation.ui.main.home.search.model

data class SearchProduct(
    val title: String,
    val imageUrl: String,
    val reviewNumber: Int,
    val registDate: String,
    val shopList: List<TagShop>
)

data class TagShop(
    val title: String
)