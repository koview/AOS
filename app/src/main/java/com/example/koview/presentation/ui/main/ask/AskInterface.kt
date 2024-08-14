package com.example.koview.presentation.ui.main.ask

import com.example.koview.data.model.response.QueryResultList

interface AskInterface {
    fun onAskClick(askDetail: QueryResultList)
    fun onAskIconClick(item: QueryResultList)
}