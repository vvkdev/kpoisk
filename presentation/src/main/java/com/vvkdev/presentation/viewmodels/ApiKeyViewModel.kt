package com.vvkdev.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vvkdev.domain.repositories.ApiKeyRepository
import com.vvkdev.domain.validation.ApiKeyValidator
import com.vvkdev.presentation.R
import com.vvkdev.presentation.base.BaseViewModel
import com.vvkdev.presentation.base.DialogUiEffect
import com.vvkdev.presentation.base.UiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiKeyViewModel @Inject constructor(
    private val apiKeyRepository: ApiKeyRepository,
    private val apiKeyValidator: ApiKeyValidator,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    val showError: StateFlow<Boolean> = savedStateHandle.getStateFlow(SHOW_ERROR, false)

    fun saveApiKey(key: String) {
        if (apiKeyValidator.isValid(key)) {
            apiKeyRepository.setApiKey(key)
            viewModelScope.launch {
                sendEffect(UiEffect.ShowShortToast(R.string.saved))
                sendEffect(DialogUiEffect.Dismiss)
            }
        } else {
            showError(true)
        }
    }

    fun showError(shouldShow: Boolean) {
        savedStateHandle[SHOW_ERROR] = shouldShow
    }

    companion object {
        private const val SHOW_ERROR = "show_error"
    }
}
