package com.vvkdev.data.di

import android.content.Context
import android.content.SharedPreferences
import com.vvkdev.data.constants.PrefsConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object SettingsModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(PrefsConstants.PREF_NAME, Context.MODE_PRIVATE)
}
