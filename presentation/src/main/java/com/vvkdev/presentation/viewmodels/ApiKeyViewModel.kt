package com.vvkdev.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vvkdev.domain.repository.ApiKeyRepository
import com.vvkdev.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiKeyViewModel @Inject constructor(
    private val apiKeyRepository: ApiKeyRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<Unit>>(UiState.Default)
    val state: StateFlow<UiState<Unit>> = _state

    fun saveApiKey(key: String) {
        viewModelScope.launch {
            _state.value = UiState.Loading
            try {
                apiKeyRepository.setApiKey(key)
                _state.value = UiState.Success(Unit)
            } catch (e: Exception) {
                _state.value = UiState.Error(e.message)
            }
        }
    }
}
