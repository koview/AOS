package com.example.koview.data.repository

import com.example.koview.data.model.BaseState
import com.example.koview.data.model.requeset.ProductsRequest
import com.example.koview.data.model.response.ProductsResponse
import com.example.koview.data.model.response.Status
import com.example.koview.data.model.runRemote
import com.example.koview.data.remote.MainApi
import com.example.koview.presentation.ui.main.home.model.Category
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val api: MainApi) : MainRepository {
    override suspend fun products(
        status: Status,
        category: Category,
        searchTerm: String,
        page: Int,
        size: Int
    ): BaseState<ProductsResponse> =
        runRemote { api.products(status, category, searchTerm, page, size) }


}