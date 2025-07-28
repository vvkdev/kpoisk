package com.vvkdev.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.vvkdev.domain.repositories.ApiKeyRepository
import com.vvkdev.domain.repositories.SettingsRepository
import com.vvkdev.theme.AccentColor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val apiKeyRepository: ApiKeyRepository,
) : ViewModel() {

    fun getAccentColor(): AccentColor {
        val color = settingsRepository.getColorAccent() ?: AccentColor.default().name
        return AccentColor.valueOf(color)
    }

    fun getApiKey(): String? = apiKeyRepository.getApiKey()
}
