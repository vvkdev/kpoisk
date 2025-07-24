package com.vvkdev.presentation.extensions

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.SearchView

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

fun SearchView.doOnQueryChanged(
    onQuerySubmit: (String) -> Unit = {},
    onQueryChange: (String) -> Unit = {},
) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            if (query.isNotBlank()) onQuerySubmit(query)
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            onQueryChange(newText)
            return true
        }
    })
}
