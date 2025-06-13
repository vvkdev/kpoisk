package com.vvkdev.presentation.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vvkdev.presentation.R
import com.vvkdev.presentation.databinding.DialogApikeyBinding
import com.vvkdev.presentation.utils.showToast
import com.vvkdev.presentation.viewmodels.ApiKeyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApiKeyDialog : DialogFragment() {

    private val viewModel: ApiKeyViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogApikeyBinding.inflate(layoutInflater)

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
                    if (apiKeyEditText.text.toString().isNotBlank()) setCancelable(false)
                }

                cancelButton.setOnClickListener { dismiss() }
                saveButton.setOnClickListener { saveApiKeyAndClose(apiKeyEditText.text.toString()) }
                apiKeyEditText.setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        saveApiKeyAndClose(apiKeyEditText.text.toString())
                        true
                    } else {
                        false
                    }
                }
            }
        }
        return dialog
    }

    private fun saveApiKeyAndClose(key: String) {
        viewModel.saveApiKey(key)
        showToast(R.string.saved)
        dismiss()
    }

    companion object {
        const val TAG = "ApiKeyDialog"
    }
}
