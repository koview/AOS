package com.example.koview.data.repository

import com.example.koview.data.model.BaseState
import com.example.koview.data.model.requeset.DeleteMyReviewRequest
import com.example.koview.data.model.response.DeleteMyReviewsResponse
import com.example.koview.data.model.response.GetMyDetailResponse
import com.example.koview.data.model.response.GetMyReviewDetailResponse
import com.example.koview.data.model.response.GetMyReviewsResponse
import com.example.koview.data.model.response.HomeResponse
import com.example.koview.data.model.response.ProductsResponse
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
        clickedReviewId: Long
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
}