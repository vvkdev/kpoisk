package com.vvkdev.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import com.vvkdev.domain.repositories.SettingsRepository
import com.vvkdev.presentation.base.BaseViewModel
import com.vvkdev.theme.AccentColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ColorViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val initialColor: AccentColor = settingsRepository.getColorAccent()
        ?.let { AccentColor.valueOf(it) }
        ?: AccentColor.default()

    var selectedColor: AccentColor = savedStateHandle.get<String>(SELECTED_COLOR)
        ?.let { AccentColor.valueOf(it) }
        ?: initialColor
        set(color) {
            field = color
            savedStateHandle[SELECTED_COLOR] = color.name
            _shouldRestart.value = color != initialColor
        }

    private val _shouldRestart = MutableStateFlow(selectedColor != initialColor)
    val shouldRestart = _shouldRestart.asStateFlow()

    fun saveAccentColor() {
        settingsRepository.setColorAccent(selectedColor.name)
    }

    companion object {
        private const val SELECTED_COLOR = "selected_color"
    }
}
