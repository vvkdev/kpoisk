package com.vvkdev.data.repositories

import android.content.SharedPreferences
import androidx.core.content.edit
import com.vvkdev.domain.repositories.SettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : SettingsRepository {

    override fun setColorAccent(color: String) {
        sharedPreferences.edit { putString(ACCENT_COLOR, color) }
    }

    override fun getColorAccent(): String? =
        sharedPreferences.getString(ACCENT_COLOR, null)

    private companion object {
        const val ACCENT_COLOR = "accent_color"
    }
}
