package com.vvkdev.data.di

import com.vvkdev.data.repository.FilmsRepositoryImpl
import com.vvkdev.domain.repository.FilmsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideFilmRepository(impl: FilmsRepositoryImpl): FilmsRepository
}
