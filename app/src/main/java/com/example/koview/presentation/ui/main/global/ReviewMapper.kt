package com.example.koview.presentation.ui.main.global

import com.example.koview.data.model.response.CoviewReviewList
import com.example.koview.data.model.response.ReviewDetailList
import com.example.koview.presentation.ui.main.coview.model.CoviewUiData

fun CoviewReviewList.toCoviewUiData(
    myProfileImgUrl: String?,
) = CoviewUiData(
    reviewId = reviewId,
    writer = writer,
    profileImage = profileImage?.url,
    purchaseLinkList = purchaseLinkList,
    totalCommentCount = totalCommentCount,
    totalLikeCount = totalLikeCount,
    content = content,
    createdAt = createdAt,
    imageList = imageList.map { image -> image?.url },
    myProfileImage = myProfileImgUrl,
    isLiked = isLiked,
)

fun ReviewDetailList.toCoviewUiData(
    myProfileImgUrl: String?,
) = CoviewUiData(
    reviewId = reviewId,
    writer = writer,
    profileImage = profileImage?.url,
    purchaseLinkList = purchaseLinkList,
    totalCommentCount = totalCommentCount,
    totalLikeCount = totalLikeCount,
    content = content,
    createdAt = createdAt,
    imageList = imageList.map { image -> image?.url },
    myProfileImage = myProfileImgUrl,
    isLiked = isLiked,
)