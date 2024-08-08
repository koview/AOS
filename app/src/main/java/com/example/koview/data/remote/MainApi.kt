package com.example.koview.data.remote

import com.example.koview.data.model.response.HomeResponse
import retrofit2.Response
import retrofit2.http.GET

interface MainApi {

    @GET("home")
    suspend fun home(): Response<HomeResponse>
}