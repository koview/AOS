package com.example.koview.data.repository

import com.example.koview.data.model.BaseState
import com.example.koview.data.model.requeset.DeleteMyReviewRequest
import com.example.koview.data.model.response.DeleteMyReviewsResponse
import com.example.koview.data.model.response.GetMyDetailResponse
import com.example.koview.data.model.response.GetMyReviewDetailResponse
import com.example.koview.data.model.response.GetMyReviewsResponse
import com.example.koview.data.model.response.HomeResponse
import com.example.koview.data.model.response.ProductReviewResponse
import com.example.koview.data.model.response.ProductsResponse
import com.example.koview.data.model.response.QueryAnswerResponse
import com.example.koview.data.model.response.QueryResponse
import com.example.koview.data.model.response.ReviewDetailResponse
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
        params: DeleteMyReviewRequest
    ): BaseState<DeleteMyReviewsResponse>
  
    suspend fun getProducts(
          status: Status,
          category: Category? = null,
          searchTerm: String? = "",
          page: Int,
          size: Int
      ): BaseState<ProductsResponse>

    suspend fun home(): BaseState<HomeResponse>

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
  
}