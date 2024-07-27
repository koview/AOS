package com.example.koview.presentation.ui.main.coview.model

data class CoviewUiData(
    var nickname: String = "",
    var profileImage: String = "",
    var likeCount: Int = 0,
    var commentCount: Int = 0,
    var likeState: Boolean = false,
    var contents: String = "",
    var date: String = ""
)
