package com.example.koview.data.model.response

data class AddQueryImageResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: List<ImageDTO>,
)
