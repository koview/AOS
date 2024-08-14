package com.example.koview.data.remote

import com.example.koview.data.model.requeset.DeleteMyReviewRequest
import com.example.koview.data.model.response.DeleteMyReviewsResponse
import com.example.koview.data.model.response.GetMyDetailResponse
import com.example.koview.data.model.response.GetMyReviewDetailResponse
import com.example.koview.data.model.response.GetMyReviewsResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import com.example.koview.data.model.response.HomeResponse
import com.example.koview.data.model.response.ProductReviewResponse
import com.example.koview.data.model.response.ProductsResponse
import com.example.koview.data.model.response.QueryAnswerResponse
import com.example.koview.data.model.response.QueryResponse
import com.example.koview.data.model.response.ReviewDetailResponse
import com.example.koview.data.model.response.Status
import com.example.koview.presentation.ui.main.home.model.Category
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApi {


    // 내 프로필 조회
    @GET("mypage/mydetail")
    suspend fun getMyDetail(): Response<GetMyDetailResponse>

    // 내 리뷰 전체 조회
    @GET("mypage/myreviews")
    suspend fun getMyReviews(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<GetMyReviewsResponse>

    // 내 리뷰 상세 조회
    @GET("mypage/myreviews/detail")
    suspend fun getMyReviewDetail(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("clickedReviewId") clickedReviewId: Long
    ): Response<GetMyReviewDetailResponse>

    // 내 리뷰 리스트 삭제
    @HTTP(method = "DELETE", path = "mypage/myreviews/delete", hasBody = true)
    suspend fun deleteMyReviews(
        @Body params: DeleteMyReviewRequest
    ): Response<DeleteMyReviewsResponse>

    @GET("products")
    suspend fun getProducts(
        @Query("status") status: Status,
        @Query("category") category: Category?,
        @Query("searchTerm") searchTerm: String?,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<ProductsResponse>

    @GET("home")
    suspend fun home(): Response<HomeResponse>

    @GET("reviews/detail")
    suspend fun getReviewDetails(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("clickedReviewId") clickedReviewId: Long
    ): Response<ReviewDetailResponse>

    @GET("products/{productId}/reviews")
    suspend fun getProductReview(
        @Path("productId") productId: Long,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<ProductReviewResponse>

    @GET("queries")
    suspend fun getQueries(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<QueryResponse>

    @GET("queries/{queryId}/answers")
    suspend fun getQueryAnswers(
        @Path("queryId") queryId: Long,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<QueryAnswerResponse>
}