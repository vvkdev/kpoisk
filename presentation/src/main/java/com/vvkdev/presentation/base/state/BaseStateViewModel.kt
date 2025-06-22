package com.vvkdev.presentation.base.state

import com.vvkdev.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseStateViewModel<T> : BaseViewModel() {

    private val _uiState = MutableStateFlow<UiState<T>>(UiState.Default)
    val uiState: StateFlow<UiState<T>> = _uiState.asStateFlow()

    protected fun updateState(newState: UiState<T>) {
        _uiState.value = newState
    }

    abstract fun retry()
}
