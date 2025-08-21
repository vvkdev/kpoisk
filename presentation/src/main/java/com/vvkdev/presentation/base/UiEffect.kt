package com.vvkdev.presentation.base

import androidx.annotation.StringRes

internal sealed class UiEffect {
    internal data class ShowShortToast(@StringRes val messageRes: Int) : UiEffect()
}

internal sealed class DialogUiEffect : UiEffect() {
    internal object Dismiss : DialogUiEffect()
}
