package com.example.koview.data.remote

import com.example.koview.data.model.requeset.ProductsRequest
import com.example.koview.data.model.response.ProductsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface MainApi {

    @GET("products")
    suspend fun products(
        @Body params: ProductsRequest
    ): Response<ProductsResponse>
}