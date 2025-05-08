package com.vvkdev.presentation.screens.apikey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vvkdev.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiKeyViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {

    private val _navigateToFilms = MutableSharedFlow<Unit>(replay = 0)
    val navigateToFilms: SharedFlow<Unit> = _navigateToFilms

    fun saveApiKey(key: String) {
        viewModelScope.launch {
            repository.saveApiKey(key)
            _navigateToFilms.emit(Unit)
        }
    }
}
