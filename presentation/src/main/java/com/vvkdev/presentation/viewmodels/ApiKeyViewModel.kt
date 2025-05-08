package com.vvkdev.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vvkdev.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiKeyViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {

    fun saveApiKey(key: String) {
        viewModelScope.launch {
            repository.saveApiKey(key)
        }
    }
}
