package com.vvkdev.presentation.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vvkdev.domain.repository.SettingsRepository
import com.vvkdev.presentation.R
import com.vvkdev.presentation.databinding.DialogColorBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ColorDialog : DialogFragment() {
    @Inject
    lateinit var settingsRepository: SettingsRepository
    private var selectedColor = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogColorBinding.inflate(layoutInflater)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.select_color)
            .setView(binding.root)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.save, null)
            .create()

        dialog.setOnShowListener {
            val cancelButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            val saveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)

            setupColorButtonsListeners(binding)
            cancelButton.setOnClickListener { dismiss() }
            saveButton.setOnClickListener {
                if (selectedColor.isBlank()) {
                    binding.textView.text = getString(R.string.color_is_not_selected)
                    binding.textView.visibility = View.VISIBLE
                } else {
                    saveButton.isEnabled = false
                    cancelButton.isEnabled = false
                    binding.gridLayout.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                    settingsRepository.setColorAccent(selectedColor)
                    ActivityCompat.recreate(requireActivity())
                    dismiss()
                }
            }
        }
        return dialog
    }

    private fun setupColorButtonsListeners(binding: DialogColorBinding) {
        with(binding) {
            val colorButtons = listOf(
                blueButton, lilacButton, redButton, orangeButton,
                greenButton, yellowButton, beigeButton, greyButton
            )
            colorButtons.forEach {
                it.setOnClickListener { button ->
                    selectColor(button as MaterialButton, gridLayout)
                    textView.text = getString(R.string.app_will_be_restarted)
                    textView.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun selectColor(button: MaterialButton, gridLayout: GridLayout) {
        for (i in 0 until gridLayout.childCount) {
            val child = gridLayout.getChildAt(i)
            if (child is MaterialButton) {
                child.text = ""
            }
        }
        button.setText(R.string.checkmark)
        selectedColor = button.tag.toString()
    }

    companion object {
        const val TAG = "ColorDialog"
    }
}
