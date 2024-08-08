package com.example.koview.app.di

import com.example.koview.data.repository.AuthRepository
import com.example.koview.data.repository.AuthRepositoryImpl
import com.example.koview.data.repository.IntroRepository
import com.example.koview.data.repository.IntroRepositoryImpl
import com.example.koview.data.repository.MainRepository
import com.example.koview.data.repository.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindIntroRepository(introRepositoryImpl: IntroRepositoryImpl): IntroRepository

    @Binds
    abstract fun bindMainRepository(globalRepositoryImpl: MainRepositoryImpl): MainRepository

}