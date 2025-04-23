package com.vvkdev.data.di

import android.content.Context
import androidx.room.Room
import com.vvkdev.data.local.KpoiskDatabase
import com.vvkdev.data.local.dao.FilmDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): KpoiskDatabase =
        Room.databaseBuilder(context, KpoiskDatabase::class.java, "kpoisk-db").build()

    @Provides
    @Singleton
    fun provideFilmDao(db: KpoiskDatabase): FilmDao = db.filmDao()
}
