package com.vvkdev.presentation.viewmodels

import AccentColor
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.vvkdev.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ColorViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var color: String
        get() = savedStateHandle.get<String>(SELECTED_COLOR)
            ?: settingsRepository.getColorAccent()
            ?: AccentColor.default().name
        set(value) = savedStateHandle.set(SELECTED_COLOR, value)

    var showRestartMessage: Boolean
        get() = savedStateHandle.get<Boolean>(SHOW_RESTART_MSG) ?: false
        set(value) = savedStateHandle.set(SHOW_RESTART_MSG, value)

    fun saveAccentColor() {
        settingsRepository.setColorAccent(color)
    }

    companion object {
        private const val SELECTED_COLOR = "selected_color"
        private const val SHOW_RESTART_MSG = "show_restart_msg"
    }
}
