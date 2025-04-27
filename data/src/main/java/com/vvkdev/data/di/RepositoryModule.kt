package com.vvkdev.data.di

import com.vvkdev.data.repository.FilmsRepositoryImpl
import com.vvkdev.data.repository.SettingsRepositoryImpl
import com.vvkdev.domain.repository.FilmsRepository
import com.vvkdev.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository

    @Binds
    fun provideFilmRepository(impl: FilmsRepositoryImpl): FilmsRepository
}
