package com.vvkdev.data.di

import com.vvkdev.data.remote.service.FilmsService
import com.vvkdev.domain.repository.ApiKeyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.kinopoisk.dev/v1.4/"
    private const val API_KEY_HEADER = "X-API-KEY"

    @Provides
    @Singleton
    fun provideOkHttpClient(apiKeyRepository: ApiKeyRepository): OkHttpClient =
        OkHttpClient.Builder().addInterceptor { chain ->
            val request = chain
                .request()
                .newBuilder()
                .addHeader(API_KEY_HEADER, apiKeyRepository.getApiKey() ?: "")
                .build()
            chain.proceed(request)
        }.build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Provides
    @Singleton
    fun provideFilmsService(retrofit: Retrofit): FilmsService =
        retrofit.create(FilmsService::class.java)
}
