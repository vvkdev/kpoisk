package com.vvkdev.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.vvkdev.domain.repository.ApiKeyRepository
import com.vvkdev.domain.validation.ApiKeyValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ApiKeyViewModel @Inject constructor(
    private val apiKeyRepository: ApiKeyRepository,
    private val apiKeyValidator: ApiKeyValidator,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _showError = MutableStateFlow(savedStateHandle.get<Boolean>(SHOW_ERROR) ?: false)
    val showError: StateFlow<Boolean> = _showError.asStateFlow()

    fun saveApiKey(key: String): Boolean {
        return if (apiKeyValidator.isValid(key)) {
            apiKeyRepository.setApiKey(key)
            true
        } else {
            showError(true)
            false
        }
    }

    fun showError(shouldShow: Boolean) {
        _showError.value = shouldShow
        savedStateHandle[SHOW_ERROR] = shouldShow
    }

    companion object {
        private const val SHOW_ERROR = "show_error"
    }
}
