package com.vvkdev.data.di

import com.vvkdev.data.remote.service.FilmsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

interface RetrofitConfig {
    val baseUrl: String
    val mediaType: MediaType
    val json: Json
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofitConfig(json: Json): RetrofitConfig =
        object : RetrofitConfig {
            override val baseUrl = "https://api.kinopoisk.dev/v1.4/"
            override val mediaType = "application/json".toMediaType()
            override val json = json
        }

    @Provides
    @Singleton
    fun provideFilmsService(config: RetrofitConfig): FilmsService {
        return Retrofit.Builder()
            .baseUrl(config.baseUrl)
            .addConverterFactory(config.json.asConverterFactory(config.mediaType))
            .build()
            .create(FilmsService::class.java)
    }
}
