package com.example.koview.presentation.ui.main.ask.model

import com.example.koview.presentation.ui.main.global.product.model.Review
import com.example.koview.presentation.ui.main.global.product.model.TagShop
import java.io.Serializable

data class AskData(
    val title: String = "",
    val contents: String = "",
    val viewCount: Int = 0,
    val answerCount: Int = 0,
    val askCount: Int = 0,
    val askImage: String = "",
    val nickname: String = "",
    val createdAt: String = "",
    val shopList: List<TagShop>,
    val reviewList: List<Review>
) : Serializable
