package com.example.koview.data.repository

import com.example.koview.data.model.BaseState
import com.example.koview.data.model.requeset.ProductsRequest
import com.example.koview.data.model.response.ProductsResponse

interface MainRepository {

    suspend fun products(
        body: ProductsRequest
    ): BaseState<ProductsResponse>

}