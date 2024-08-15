package com.example.koview.presentation.ui.main.home.model

data class AskUiData(
    val content: String = "",
    val viewCount: Long = 0L,
    val answerCount: Long = 0L,
    val withQueryCount: Long = 0L,
    val isWithQuery: Boolean = false,
    val askImage: String = "",
)
