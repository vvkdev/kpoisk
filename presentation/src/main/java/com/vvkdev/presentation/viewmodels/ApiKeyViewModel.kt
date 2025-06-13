package com.vvkdev.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.vvkdev.domain.repository.ApiKeyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ApiKeyViewModel @Inject constructor(
    private val apiKeyRepository: ApiKeyRepository,
) : ViewModel() {

    fun saveApiKey(key: String) {
        apiKeyRepository.setApiKey(key)
    }
}
