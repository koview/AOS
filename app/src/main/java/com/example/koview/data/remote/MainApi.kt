package com.example.koview.data.remote

import com.example.koview.data.model.response.DeleteMyReviewsResponse
import com.example.koview.data.model.response.GetMyDetailResponse
import com.example.koview.data.model.response.GetMyReviewDetailResponse
import com.example.koview.data.model.response.GetMyReviewsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
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
    @DELETE("mypage/myreviews/delete")
    suspend fun deleteMyReviews(
        @Body reviewIdList: List<Long>
    ): Response<DeleteMyReviewsResponse>
}