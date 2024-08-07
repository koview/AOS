package com.example.koview.data.repository

import com.example.koview.data.model.BaseState
import com.example.koview.data.model.response.HomeResponse
import com.example.koview.data.model.runRemote

import com.example.koview.data.remote.MainApi
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val api: MainApi) : MainRepository {

    override suspend fun home(): BaseState<HomeResponse> = runRemote { api.home() }
}