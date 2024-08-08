package com.example.koview.data.repository

import com.example.koview.data.model.BaseState
import com.example.koview.data.model.response.HomeResponse

interface MainRepository {

    suspend fun home(): BaseState<HomeResponse>
}