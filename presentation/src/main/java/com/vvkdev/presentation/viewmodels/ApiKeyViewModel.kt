package com.vvkdev.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vvkdev.domain.repository.ApiKeyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiKeyViewModel @Inject constructor(
    private val apiKeyRepository: ApiKeyRepository,
) : ViewModel() {

    fun saveApiKey(key: String) {
        viewModelScope.launch { apiKeyRepository.setApiKey(key) }
    }
}
