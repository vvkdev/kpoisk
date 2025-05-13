package com.vvkdev.presentation.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment

fun DialogFragment.showToast(@StringRes messageRes: Int) {
    showToast(requireContext(), messageRes)
}

private fun showToast(context: Context, @StringRes messageRes: Int) {
    Toast.makeText(context, messageRes, Toast.LENGTH_SHORT).show()
}
