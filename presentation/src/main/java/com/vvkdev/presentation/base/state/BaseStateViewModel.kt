package com.vvkdev.presentation.base.state

import com.vvkdev.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseStateViewModel<DM> : BaseViewModel() {

    private val _uiState = MutableStateFlow<UiState<DM>>(UiState.Default)
    val uiState: StateFlow<UiState<DM>> = _uiState.asStateFlow()

    protected fun updateState(newState: UiState<DM>) {
        _uiState.value = newState
    }

    abstract fun retry()
}
