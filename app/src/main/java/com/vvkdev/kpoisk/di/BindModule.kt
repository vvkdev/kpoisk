package com.vvkdev.kpoisk.di

import com.vvkdev.data.repositories.ApiKeyRepositoryImpl
import com.vvkdev.data.repositories.FilmRepositoryImpl
import com.vvkdev.data.repositories.SettingsRepositoryImpl
import com.vvkdev.domain.repositories.ApiKeyRepository
import com.vvkdev.domain.repositories.FilmRepository
import com.vvkdev.domain.repositories.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface BindModule {

    @Binds
    fun bindApiKeyRepository(impl: ApiKeyRepositoryImpl): ApiKeyRepository

    @Binds
    fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository

    @Binds
    fun provideFilmRepository(impl: FilmRepositoryImpl): FilmRepository
}
