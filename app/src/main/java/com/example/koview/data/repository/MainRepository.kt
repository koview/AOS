package com.example.koview.data.repository

import com.example.koview.data.model.BaseState
import com.example.koview.data.model.requeset.CoviewCommentRequest
import com.example.koview.data.model.response.AddCoviewCommentResponse
import com.example.koview.data.model.response.ReviewLikeResponse
import com.example.koview.data.model.response.DeleteMyReviewsResponse
import com.example.koview.data.model.response.GetCoviewCommentsResponse
import com.example.koview.data.model.response.GetMyDetailResponse
import com.example.koview.data.model.response.GetMyReviewDetailResponse
import com.example.koview.data.model.response.GetMyReviewsResponse
import com.example.koview.data.model.response.GetCoviewReviewsResponse
import com.example.koview.data.model.response.HomeResponse
import com.example.koview.data.model.response.ProductsResponse
import com.example.koview.data.model.response.Status
import com.example.koview.presentation.ui.main.home.model.Category

interface MainRepository {

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

    suspend fun getProducts(
        status: Status,
        category: Category? = null,
        searchTerm: String? = "",
        page: Int,
        size: Int
    ): BaseState<ProductsResponse>

    suspend fun home(): BaseState<HomeResponse>

    suspend fun getCoviewReviews(
        page: Int,
        size: Int
    ): BaseState<GetCoviewReviewsResponse>

    suspend fun getCoviewComments(
        reviewId: Long,
        page: Int,
        size: Int
    ): BaseState<GetCoviewCommentsResponse>

    suspend fun addCoviewComment(
        reviewId: Long,
        body: CoviewCommentRequest
    ): BaseState<AddCoviewCommentResponse>

    suspend fun addReviewLike(
        reviewId: Long
    ): BaseState<ReviewLikeResponse>

    suspend fun deleteReviewLike(
        reviewId: Long
    ): BaseState<ReviewLikeResponse>
}