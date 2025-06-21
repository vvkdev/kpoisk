package com.vvkdev.presentation.utils

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.showShortToast(@StringRes messageRes: Int) {
    Toast.makeText(requireContext(), messageRes, Toast.LENGTH_SHORT).show()
}
