package com.example.koview.presentation.ui.main.coview.model

data class CoviewUiData(
    var reviewId: Long = 0L,
    var writer: String = "",
    var profileImage: String? = "",
    var totalCommentCount: Long = 0L,
    var totalLikesCount: Long = 0L,
    var content: String = "",
    var createdAt: String = "",
    var imageList: List<String?> = emptyList(),
    var myProfileImage: String? = "",
    var isLiked: Boolean = false,
    var isExpanded: Boolean = false
)