package com.vvkdev.presentation.dialogs

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Enter API key")
            .setView(binding.root)
            .setCancelable(true)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Save", null)
            .create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                viewModel.saveApiKey(binding.apiKeyEditText.text.toString())
                Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }
        return dialog
    }

    companion object {
        const val TAG = "ApiKeyDialog"
    }
}
