package com.vvkdev.presentation.dialogs

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vvkdev.presentation.databinding.DialogApikeyBinding
import com.vvkdev.presentation.viewmodels.ApiKeyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApiKeyDialog : DialogFragment() {

    private val viewModel: ApiKeyViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogApikeyBinding.inflate(layoutInflater)
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle("Enter API key")
            .setView(binding.root)
            .setCancelable(true)
            .setPositiveButton("Save") { _, _ ->
                viewModel.saveApiKey(binding.apiKeyEditText.text.toString())
                Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .create()
    }

    companion object {
        const val TAG = "ApiKeyDialog"
    }
}
