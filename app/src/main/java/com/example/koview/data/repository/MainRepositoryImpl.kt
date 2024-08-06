package com.example.koview.data.repository

import com.example.koview.data.model.BaseState
import com.example.koview.data.model.requeset.ProductsRequest
import com.example.koview.data.model.response.ProductsResponse
import com.example.koview.data.model.runRemote
import com.example.koview.data.remote.MainApi
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val api: MainApi) : MainRepository {
    override suspend fun products(body: ProductsRequest): BaseState<ProductsResponse> =
        runRemote { api.products(body) }

}