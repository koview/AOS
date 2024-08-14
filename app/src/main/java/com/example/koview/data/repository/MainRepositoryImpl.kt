package com.example.koview.data.repository

import com.example.koview.data.model.BaseState
import com.example.koview.data.model.requeset.CoviewCommentRequest
import com.example.koview.data.model.response.AddCoviewCommentResponse
import com.example.koview.data.model.response.ReviewLikeResponse
import com.example.koview.data.model.requeset.DeleteMyReviewRequest
import com.example.koview.data.model.response.DeleteMyReviewsResponse
import com.example.koview.data.model.response.GetCoviewCommentsResponse
import com.example.koview.data.model.response.GetCoviewReviewsResponse
import com.example.koview.data.model.response.GetMyDetailResponse
import com.example.koview.data.model.response.GetMyReviewDetailResponse
import com.example.koview.data.model.response.GetMyReviewsResponse
import com.example.koview.data.model.response.HomeResponse
import com.example.koview.data.model.response.ProductsResponse
import com.example.koview.data.model.response.ReviewDetailResponse
import com.example.koview.data.model.response.Status
import com.example.koview.data.model.runRemote
import com.example.koview.data.remote.MainApi
import com.example.koview.presentation.ui.main.home.model.Category
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val api: MainApi) : MainRepository {

    override suspend fun getMyDetail(): BaseState<GetMyDetailResponse> =
        runRemote { api.getMyDetail() }

    override suspend fun getMyReviews(page: Int, size: Int): BaseState<GetMyReviewsResponse> =
        runRemote { api.getMyReviews(page, size) }

    override suspend fun getMyReviewDetail(
        page: Int,
        size: Int,
        clickedReviewId: Long,
    ): BaseState<GetMyReviewDetailResponse> =
        runRemote { api.getMyReviewDetail(page, size, clickedReviewId) }

    override suspend fun deleteMyReviews(params: DeleteMyReviewRequest): BaseState<DeleteMyReviewsResponse> =
        runRemote { api.deleteMyReviews(params) }

    override suspend fun getProducts(
        status: Status,
        category: Category?,
        searchTerm: String?,
        page: Int,
        size: Int
    ): BaseState<ProductsResponse> =
        runRemote { api.getProducts(status, category, searchTerm, page, size) }

    override suspend fun home(): BaseState<HomeResponse> = runRemote { api.home() }

    override suspend fun getCoviewReviews(
        page: Int,
        size: Int,
    ): BaseState<GetCoviewReviewsResponse> = runRemote { api.getCoviewReviews(page, size) }

    override suspend fun getCoviewComments(
        reviewId: Long,
        page: Int,
        size: Int,
    ): BaseState<GetCoviewCommentsResponse> =
        runRemote { api.getCoviewComments(reviewId, page, size) }

    override suspend fun addCoviewComment(
        reviewId: Long,
        body: CoviewCommentRequest,
    ): BaseState<AddCoviewCommentResponse> =
        runRemote { api.addCoviewComment(reviewId, body) }

    override suspend fun addReviewLike(reviewId: Long): BaseState<ReviewLikeResponse> =
        runRemote { api.addReviewLike(reviewId) }

    override suspend fun deleteReviewLike(reviewId: Long): BaseState<ReviewLikeResponse> =
        runRemote { api.deleteReviewLike(reviewId) }

    override suspend fun getReviewDetails(
        page: Int,
        size: Int,
        clickedReviewId: Long
    ): BaseState<ReviewDetailResponse> = runRemote {
        api.getReviewDetails(page, size, clickedReviewId)
    }
}