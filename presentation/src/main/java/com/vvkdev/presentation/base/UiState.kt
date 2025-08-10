package com.vvkdev.presentation.base

internal sealed class UiState<out T> {
    internal data object Default : UiState<Nothing>()
    internal data object Loading : UiState<Nothing>()
    internal data class Success<T>(val data: T) : UiState<T>()
    internal data class Error(val message: String?) : UiState<Nothing>()
}
