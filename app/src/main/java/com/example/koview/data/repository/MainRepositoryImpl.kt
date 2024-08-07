package com.example.koview.data.repository

import com.example.koview.data.remote.MainApi
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val api: MainApi) : MainRepository {
}