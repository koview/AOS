package com.example.koview.app.di

import com.example.koview.data.remote.AuthApi
import com.example.koview.data.remote.IntroApi
import com.example.koview.data.remote.MainApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Singleton
    @Provides
    fun provideIntroApi(retrofit: Retrofit): IntroApi = retrofit.create(IntroApi::class.java)


    @Singleton
    @Provides
    fun provideMainApi(retrofit: Retrofit): MainApi = retrofit.create(MainApi::class.java)

}