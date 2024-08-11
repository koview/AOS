package com.example.koview.presentation.ui.main.coview.model

import com.example.koview.data.model.response.PurchaseLinkList

data class CoviewUiData(
    var reviewId: Long = 0L,
    var writer: String = "",
    var profileImage: String? = "",
    var purchaseLinkList: List<PurchaseLinkList> = emptyList(),
    var totalCommentCount: Long = 0L,
    var totalLikesCount: Long = 0L,
    var content: String = "",
    var createdAt: String = "",
    var imageList: List<String?> = emptyList(),
    var myProfileImage: String? = "",
    var isLiked: Boolean = false,
    var isExpanded: Boolean = false,
    var currentPage: Int = 0    // 현재 리뷰 사진 페이지
)