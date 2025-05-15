package com.vvkdev.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.vvkdev.domain.repository.SettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : SettingsRepository {

    override fun setColorAccent(color: String) {
        sharedPreferences.edit { putString(ACCENT_COLOR, color) }
    }

    override fun getColorAccent(): String =
        sharedPreferences.getString(ACCENT_COLOR, DEFAULT_COLOR) ?: DEFAULT_COLOR

    companion object {
        private const val ACCENT_COLOR = "accent_color"
        private const val DEFAULT_COLOR = "blue"
    }
}
