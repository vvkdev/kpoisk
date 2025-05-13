package com.vvkdev.presentation.dialogs

import android.app.Dialog
import android.os.Bundle
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
import com.vvkdev.presentation.UiState
import com.vvkdev.presentation.databinding.DialogApikeyBinding
import com.vvkdev.presentation.utils.showToast
import com.vvkdev.presentation.viewmodels.ApiKeyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
                saveButton.setOnClickListener { saveApiKey(apiKeyEditText.text.toString()) }
                apiKeyEditText.setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        saveApiKey(apiKeyEditText.text.toString())
                        true
                    } else {
                        false
                    }
                }

                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.state.collect { state ->
                            when (state) {
                                UiState.Default -> {}
                                UiState.Loading -> {
                                    progressBar.visibility = View.VISIBLE
                                    apiKeyTextInputLayout.visibility = View.GONE
                                    saveButton.isEnabled = false
                                    cancelButton.isEnabled = false
                                }

                                is UiState.Success -> {
                                    showToast(R.string.saved)
                                    dismiss()
                                }

                                is UiState.Error -> {
                                    progressBar.visibility = View.GONE
                                    apiKeyTextInputLayout.visibility = View.VISIBLE
                                    apiKeyTextInputLayout.error =
                                        state.message ?: getString(R.string.unknown_error)
                                    saveButton.isEnabled = true
                                    cancelButton.isEnabled = true
                                }
                            }
                        }
                    }
                }
            }
        }
        return dialog
    }

    private fun saveApiKey(key: String) {
        viewModel.saveApiKey(key)
    }

    companion object {
        const val TAG = "ApiKeyDialog"
    }
}
