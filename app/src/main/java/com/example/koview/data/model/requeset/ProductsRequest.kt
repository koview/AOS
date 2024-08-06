package com.example.koview.data.model.requeset

import com.example.koview.data.model.response.Status
import com.example.koview.presentation.ui.main.home.model.Category

data class ProductsRequest(
    val status: Status,
    val category: Category,
    val searchTerm: String,
    val page: Int,
    val size: Int
)
