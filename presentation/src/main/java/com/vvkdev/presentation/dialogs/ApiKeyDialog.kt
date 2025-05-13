package com.vvkdev.presentation.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import com.vvkdev.presentation.viewmodels.ApiKeyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ApiKeyDialog : DialogFragment() {

    private val viewModel: ApiKeyViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogApikeyBinding.inflate(layoutInflater)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Enter API key")
            .setView(binding.root)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Save", null)
            .create()

        dialog.setOnShowListener {
            val cancelButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            val saveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)

            with(binding) {

                apiKeyEditText.doAfterTextChanged {
                    if (apiKeyEditText.text.toString().isNotBlank()) setCancelable(false)
                }

                cancelButton.setOnClickListener { dismiss() }

                saveButton.setOnClickListener { viewModel.saveApiKey(apiKeyEditText.text.toString()) }

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
                                    Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT)
                                        .show()
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

    companion object {
        const val TAG = "ApiKeyDialog"
    }
}
