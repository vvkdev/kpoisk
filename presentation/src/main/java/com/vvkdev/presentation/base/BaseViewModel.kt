package com.vvkdev.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class BaseViewModel : ViewModel() {

    private val _effectFlow = MutableSharedFlow<UiEffect>()
    val effectFlow: SharedFlow<UiEffect> = _effectFlow

    suspend fun sendEffect(effect: UiEffect) = _effectFlow.emit(effect)
}
