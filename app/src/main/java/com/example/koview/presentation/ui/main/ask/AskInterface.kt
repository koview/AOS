package com.example.koview.presentation.ui.main.ask

import com.example.koview.presentation.ui.main.ask.model.AskData

interface AskInterface {
    fun onAskClick(askDetail: AskData)
    fun onAskIconClick(item: AskData)
}