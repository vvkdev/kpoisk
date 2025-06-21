package com.vvkdev.presentation.dialogs

import android.app.Dialog
import android.os.Bundle
import android.util.TypedValue
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isInvisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vvkdev.presentation.R
import com.vvkdev.presentation.databinding.DialogApikeyBinding
import com.vvkdev.presentation.extensions.collectWhenStarted
import com.vvkdev.presentation.extensions.showShortToast
import com.vvkdev.presentation.viewmodels.ApiKeyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApiKeyDialog : DialogFragment() {

    private val viewModel: ApiKeyViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogApikeyBinding.inflate(layoutInflater)

        val textSize = when (resources.configuration.fontScale) {
            1.15f -> 13f
            1.3f -> 12f
            1.5f -> 11f
            1.8f -> 9f
            2.0f -> 8f
            else -> null
        }
        textSize?.let { binding.apiKeyEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize) }

        collectWhenStarted(viewModel.showError) { binding.errorTextView.isInvisible = !it }

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.enter_api_key)
            .setView(binding.root)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.save, null)
            .create()

        dialog.setOnShowListener {
            val cancelButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            val saveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)

            with(binding) {
                apiKeyEditText.doAfterTextChanged {
                    viewModel.showError(false)
                    setCancelable(false)
                }

                cancelButton.setOnClickListener { dismiss() }
                saveButton.setOnClickListener { saveApiKey(apiKeyEditText.text.toString()) }
                apiKeyEditText.setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        saveApiKey(apiKeyEditText.text.toString())
                        true
                    } else {
                        false
                    }
                }
            }
        }
        return dialog
    }

    private fun saveApiKey(key: String) {
        if (viewModel.saveApiKey(key)) {
            showShortToast(R.string.saved)
            dismiss()
        }
    }

    companion object {
        const val TAG = "ApiKeyDialog"
    }
}
