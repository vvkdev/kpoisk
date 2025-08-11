package com.vvkdev.presentation.base

import android.view.View
import androidx.viewbinding.ViewBinding
import com.vvkdev.presentation.R

internal data class DialogConfig<CVB : ViewBinding>(
    val titleRes: Int,
    val contentLayoutRes: Int,
    val contentBindingBind: (View) -> CVB,
    val positiveButtonTextRes: Int = R.string.save,
    val negativeButtonTextRes: Int = R.string.cancel,
    val neutralButtonTextRes: Int = 0,
)
