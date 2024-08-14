package com.example.koview.data.remote

import com.example.koview.data.model.requeset.CoviewCommentRequest
import com.example.koview.data.model.response.ReviewLikeResponse
import com.example.koview.data.model.response.AddCoviewCommentResponse
import com.example.koview.data.model.requeset.DeleteMyReviewRequest
import com.example.koview.data.model.requeset.QueryAnswerRequest
import com.example.koview.data.model.response.DeleteMyReviewsResponse
import com.example.koview.data.model.response.GetCoviewCommentsResponse
import com.example.koview.data.model.response.GetMyDetailResponse
import com.example.koview.data.model.response.GetMyReviewDetailResponse
import com.example.koview.data.model.response.GetMyReviewsResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import com.example.koview.data.model.response.HomeResponse
import com.example.koview.data.model.response.ProductReviewResponse
import com.example.koview.data.model.response.ProductsResponse
import com.example.koview.data.model.response.QueryAnswerPostResponse
import com.example.koview.data.model.response.QueryAnswerResponse
import com.example.koview.data.model.response.QueryResponse
import com.example.koview.data.model.response.ReviewDetailResponse
import com.example.koview.data.model.response.GetCoviewReviewsResponse
import com.example.koview.data.model.response.Status
import com.example.koview.presentation.ui.main.home.model.Category
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.POST
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

    // 홈 화면
    @GET("home")
    suspend fun home(): Response<HomeResponse>

    // 코뷰 리뷰 조회
    @GET("reviews")
    suspend fun getCoviewReviews(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<GetCoviewReviewsResponse>

    // 코뷰 리뷰 검색
    @GET("review/search")
    suspend fun searchCoviewReviews(
        @Query("keyword") keyword: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<GetCoviewReviewsResponse>

    // 코뷰 특정 리뷰 댓글 조회
    @GET("comments")
    suspend fun getCoviewComments(
        @Query("reviewId") reviewId: Long,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<GetCoviewCommentsResponse>

    // 코뷰 특정 리뷰 댓글 작성
    @POST("comment/create")
    suspend fun addCoviewComment(
        @Query("reviewId") reviewId: Long,
        @Body params: CoviewCommentRequest
    ): Response<AddCoviewCommentResponse>

    // 리뷰 좋아요 추가
    @POST("likes/create")
    suspend fun addReviewLike(
        @Query("reviewId") reviewId: Long
    ): Response<ReviewLikeResponse>

    // 리뷰 좋아요 삭제
    @DELETE("likes/delete")
    suspend fun deleteReviewLike(
        @Query("reviewId") reviewId: Long
    ): Response<ReviewLikeResponse>

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

    @POST("queries/{queryId}/answer")
    suspend fun postQueryAnswer(
        @Path("queryId") queryId: Long,
        @Body params: QueryAnswerRequest
    ): Response<QueryAnswerPostResponse>
}