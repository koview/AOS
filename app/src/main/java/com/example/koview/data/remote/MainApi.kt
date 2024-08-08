package com.example.koview.data.remote

import com.example.koview.data.model.response.HomeResponse
import com.example.koview.data.model.response.ProductsResponse
import com.example.koview.data.model.response.Status
import com.example.koview.presentation.ui.main.home.model.Category
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MainApi {

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

}