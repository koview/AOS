package com.example.koview.data.repository

import com.example.koview.data.model.BaseState
import com.example.koview.data.model.response.DeleteMyReviewsResponse
import com.example.koview.data.model.response.GetMyDetailResponse
import com.example.koview.data.model.response.GetMyReviewDetailResponse
import com.example.koview.data.model.response.GetMyReviewsResponse
import com.example.koview.data.model.runRemote
import com.example.koview.data.remote.MyPageApi
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(private val api: MyPageApi): MyPageRepository {
    override suspend fun getMyDetail(): BaseState<GetMyDetailResponse> =
        runRemote { api.getMyDetail() }

    override suspend fun getMyReviews(page: Int, size: Int): BaseState<GetMyReviewsResponse> =
        runRemote { api.getMyReviews(page, size) }

    override suspend fun getMyReviewDetail(
        page: Int,
        size: Int,
        clickedReviewId: Long
    ): BaseState<GetMyReviewDetailResponse> =
        runRemote { api.getMyReviewDetail(page, size, clickedReviewId) }

    override suspend fun deleteMyReviews(reviewIdList: List<Long>): BaseState<DeleteMyReviewsResponse> =
        runRemote { api.deleteMyReviews(reviewIdList) }
}