package com.vvkdev.presentation.base

import androidx.annotation.StringRes

sealed class UiEffect {
    data class ShowShortToast(@StringRes val messageRes: Int) : UiEffect()
}

sealed class DialogUiEffect : UiEffect() {
    object Dismiss : DialogUiEffect()
}
