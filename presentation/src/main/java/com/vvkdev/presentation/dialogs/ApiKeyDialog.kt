package com.vvkdev.presentation.dialogs

import android.app.Dialog
import android.os.Bundle
import android.text.InputType
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
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

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.enter_api_key)
            .setView(binding.root)
            .create()

        collectWhenStarted(viewModel.showError) {
            binding.formatTextView.isVisible = !it
            binding.errorTextView.isVisible = it
        }

        dialog.setOnShowListener {
            binding.apiKeyEditText.doAfterTextChanged {
                viewModel.showError(false)
                setCancelable(false)
            }
        }

        with(binding) {
            apiKeyEditText.setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS) // for multiline IME_ACTION_DONE working
            buttons.negativeButton.text = getString(R.string.cancel)
            buttons.negativeButton.setOnClickListener { dismiss() }
            buttons.positiveButton.text = getString(R.string.save)
            buttons.positiveButton.setOnClickListener { saveApiKey(apiKeyEditText.text.toString()) }
            apiKeyEditText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    saveApiKey(apiKeyEditText.text.toString())
                    true
                } else {
                    false
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
