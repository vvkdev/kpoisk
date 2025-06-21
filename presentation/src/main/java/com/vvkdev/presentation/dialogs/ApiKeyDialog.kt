package com.vvkdev.presentation.dialogs

import android.app.Dialog
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vvkdev.presentation.R
import com.vvkdev.presentation.databinding.DialogApikeyBinding
import com.vvkdev.presentation.extensions.showShortToast
import com.vvkdev.presentation.viewmodels.ApiKeyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.showError.collect {
                    binding.errorTextView.visibility = if (it) View.VISIBLE else View.INVISIBLE
                }
            }
        }

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
