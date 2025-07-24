package com.vvkdev.presentation.extensions

import android.view.inputmethod.EditorInfo
import android.widget.EditText

fun EditText.onDoneAction(action: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            action()
            true
        } else {
            false
        }
    }
}
