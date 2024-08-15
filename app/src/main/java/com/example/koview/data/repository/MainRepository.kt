package com.example.koview.data.repository

import com.example.koview.data.model.BaseState
import com.example.koview.data.model.requeset.CreateReviewRequest
import com.example.koview.data.model.requeset.CoviewCommentRequest
import com.example.koview.data.model.requeset.DeleteMyReviewRequest
import com.example.koview.data.model.response.CreateReviewResponse
import com.example.koview.data.model.requeset.QueryAnswerRequest
import com.example.koview.data.model.response.AddCoviewCommentResponse
import com.example.koview.data.model.response.DeleteMyReviewsResponse
import com.example.koview.data.model.response.GetCoviewCommentsResponse
import com.example.koview.data.model.response.GetCoviewReviewsResponse
import com.example.koview.data.model.response.GetMyDetailResponse
import com.example.koview.data.model.response.GetMyReviewDetailResponse
import com.example.koview.data.model.response.GetMyReviewsResponse
import com.example.koview.data.model.response.HomeResponse
import com.example.koview.data.model.response.ProductReviewResponse
import com.example.koview.data.model.response.ProductsResponse
import com.example.koview.data.model.response.QueryAnswerPostResponse
import com.example.koview.data.model.response.QueryAnswerResponse
import com.example.koview.data.model.response.QueryResponse
import com.example.koview.data.model.response.ReviewDetailResponse
import com.example.koview.data.model.response.ReviewImageResponse
import com.example.koview.data.model.response.ReviewLikeResponse
import com.example.koview.data.model.response.Status
import com.example.koview.data.model.response.WithQueryResponse
import com.example.koview.presentation.ui.main.home.model.Category
import okhttp3.MultipartBody

interface MainRepository {

    suspend fun getMyDetail(): BaseState<GetMyDetailResponse>

    suspend fun getMyReviews(
        page: Int,
        size: Int
    ): BaseState<GetMyReviewsResponse>

    suspend fun deleteMyReviews(
        params: DeleteMyReviewRequest
    ): BaseState<DeleteMyReviewsResponse>

    suspend fun postReviewImage(
        images: List<MultipartBody.Part>
    ): BaseState<ReviewImageResponse>

    suspend fun createReview(
        params: CreateReviewRequest
    ): BaseState<CreateReviewResponse>

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

    suspend fun searchCoviewReviews(
        keyword: String,
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

    suspend fun getReviewDetails(
        page: Int,
        size: Int,
        clickedReviewId: Long
    ): BaseState<ReviewDetailResponse>

    suspend fun getProductReview(
        productId: Long,
        page: Int = 1,
        size: Int = 20
    ): BaseState<ProductReviewResponse>

    suspend fun getQueries(
        page: Int = 1,
        size: Int = 20
    ): BaseState<QueryResponse>

    suspend fun getQueryAnswers(
        queryId: Long,
        page: Int = 1,
        size: Int = 20
    ): BaseState<QueryAnswerResponse>

    suspend fun postQueryAnswer(
        queryId: Long,
        params: QueryAnswerRequest
    ): BaseState<QueryAnswerPostResponse>

    suspend fun postWithQuery(
        queryId: Long
    ): BaseState<WithQueryResponse>

    suspend fun deleteWithQuery(
        queryId: Long
    ): BaseState<WithQueryResponse>
}