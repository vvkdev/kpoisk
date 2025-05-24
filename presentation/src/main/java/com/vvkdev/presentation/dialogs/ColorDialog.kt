package com.vvkdev.presentation.dialogs

import ColoredTheme
import android.app.Dialog
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vvkdev.domain.repository.SettingsRepository
import com.vvkdev.presentation.R
import com.vvkdev.presentation.databinding.DialogColorBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.vvkdev.theme.R as ThemeR

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

            setupColorButtons(binding)
            cancelButton.setOnClickListener { dismiss() }
            saveButton.setOnClickListener {
                if (selectedColor.isBlank()) {
                    binding.textView.text = getString(R.string.color_is_not_selected)
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

    private fun setupColorButtons(binding: DialogColorBinding) {
        binding.gridLayout.removeAllViews()

        ColoredTheme.entries.forEach { theme ->
            val button = MaterialButton(requireContext()).apply {
                tag = theme.name
                textSize = 20F
                typeface = Typeface.DEFAULT_BOLD

                layoutParams = GridLayout.LayoutParams().apply {
                    val size = resources.getDimensionPixelSize(ThemeR.dimen.icon_medium)
                    val margin = resources.getDimensionPixelSize(ThemeR.dimen.indent_medium)
                    width = size
                    height = size
                    setMargins(margin, margin, margin, margin)
                }

                background = GradientDrawable().apply {
                    shape = GradientDrawable.OVAL
                    color = ContextCompat.getColorStateList(context, theme.colorRes)
                }

                setOnClickListener { button ->
                    selectColor(button as MaterialButton, binding.gridLayout)
                    binding.textView.text = getString(R.string.app_will_be_restarted)
                }
            }
            binding.gridLayout.addView(button)
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
