package com.vvkdev.kpoisk.di

import com.vvkdev.data.repository.ApiKeyRepositoryImpl
import com.vvkdev.data.repository.FilmRepositoryImpl
import com.vvkdev.data.repository.SettingsRepositoryImpl
import com.vvkdev.domain.repository.ApiKeyRepository
import com.vvkdev.domain.repository.FilmRepository
import com.vvkdev.domain.repository.SettingsRepository
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
