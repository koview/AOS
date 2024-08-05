package com.example.koview.data.repository

import com.example.koview.data.model.BaseState
import com.example.koview.data.model.response.DeleteMyReviewsResponse
import com.example.koview.data.model.response.GetMyDetailResponse
import com.example.koview.data.model.response.GetMyReviewDetailResponse
import com.example.koview.data.model.response.GetMyReviewsResponse

interface MyPageRepository {

    suspend fun getMyDetail(): BaseState<GetMyDetailResponse>

    suspend fun getMyReviews(
        page: Int,
        size: Int
    ): BaseState<GetMyReviewsResponse>

    suspend fun getMyReviewDetail(
        page: Int,
        size: Int,
        clickedReviewId: Long
    ): BaseState<GetMyReviewDetailResponse>

    suspend fun deleteMyReviews(
        reviewIdList: List<Long>
    ): BaseState<DeleteMyReviewsResponse>
}