package com.example.koview.presentation.ui.main.global.product.model

import com.example.koview.presentation.ui.main.home.model.Category
import java.io.Serializable

data class Product(
    val title: String,
    val imageUrl: String,
    val reviewNumber: Int,
    val registDate: String,
    val isWarning: Boolean,
    val isHot: Boolean,
    val category: Category,
    val shopList: List<TagShop>,
    val reviewList: List<Review>
) : Serializable

data class TagShop(
    val title: String,
    val productUrl: String,
    val isVerify: Boolean
) : Serializable

data class Review(
    val nickname: String,
    val content: String,
    val imageUrl: List<String>,
    val likeNumber: Int,
    val commentNumber: Int,
    val date: String
) : Serializable