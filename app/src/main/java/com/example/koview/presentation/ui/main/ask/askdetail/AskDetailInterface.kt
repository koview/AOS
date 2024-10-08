package com.example.koview.presentation.ui.main.ask.askdetail

import com.example.koview.data.model.response.QueryAnswerList
import com.example.koview.presentation.ui.main.global.product.model.Review

interface AskDetailInterface {
    fun onClickTag(url: String)
    fun onClickLike(item: QueryAnswerList)
    fun onClickItem(item: QueryAnswerList)
}