package com.vvkdev.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.vvkdev.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ColorViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    fun saveAccentColor(color: String) {
        settingsRepository.setColorAccent(color)
    }
}
