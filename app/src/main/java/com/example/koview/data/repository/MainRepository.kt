package com.example.koview.data.repository

import com.example.koview.data.model.BaseState
import com.example.koview.data.model.response.HomeResponse
import com.example.koview.data.model.response.ProductsResponse
import com.example.koview.data.model.response.Status
import com.example.koview.presentation.ui.main.home.model.Category

interface MainRepository {

    suspend fun getProducts(
        status: Status,
        category: Category? = null,
        searchTerm: String? = "",
        page: Int,
        size: Int
    ): BaseState<ProductsResponse>

    suspend fun home(): BaseState<HomeResponse>

}