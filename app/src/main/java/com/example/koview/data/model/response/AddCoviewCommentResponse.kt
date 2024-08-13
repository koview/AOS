package com.example.koview.data.model.response

data class AddCoviewCommentResponse (
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: CoviewCommentItem,
)