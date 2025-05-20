package com.vvkdev.data.di

import com.vvkdev.data.crypto.CryptoService
import com.vvkdev.data.crypto.CryptoServiceImpl
import com.vvkdev.data.repository.ApiKeyRepositoryImpl
import com.vvkdev.data.repository.FilmsRepositoryImpl
import com.vvkdev.data.repository.SettingsRepositoryImpl
import com.vvkdev.domain.repository.ApiKeyRepository
import com.vvkdev.domain.repository.FilmsRepository
import com.vvkdev.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface BindModule {

    @Binds
    fun bindCryptoService(impl: CryptoServiceImpl): CryptoService

    @Binds
    fun bindApiKeyRepository(impl: ApiKeyRepositoryImpl): ApiKeyRepository

    @Binds
    fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository

    @Binds
    fun provideFilmsRepository(impl: FilmsRepositoryImpl): FilmsRepository
}
