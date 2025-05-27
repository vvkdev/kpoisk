package com.vvkdev.presentation.dialogs

import ColoredTheme
import android.app.Dialog
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
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

        setupColorButtons(binding)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.select_color)
            .setView(binding.root)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.save, null)
            .create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener { dismiss() }
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                if (selectedColor.isBlank()) {
                    binding.textView.text = getString(R.string.color_is_not_selected)
                } else {
                    settingsRepository.setColorAccent(selectedColor)
                    ActivityCompat.recreate(requireActivity())
                    dismiss()
                }
            }
        }
        return dialog
    }

    private fun setupColorButtons(binding: DialogColorBinding) {
        val size = resources.getDimensionPixelSize(ThemeR.dimen.icon_medium)
        val margin = resources.getDimensionPixelSize(ThemeR.dimen.indent_medium)

        ColoredTheme.entries.forEach { theme ->
            val button = MaterialButton(requireContext()).apply {
                tag = theme.name

                layoutParams = GridLayout.LayoutParams().apply {
                    width = size
                    height = size
                    setMargins(margin, margin, margin, margin)
                }

                background = GradientDrawable().apply {
                    shape = GradientDrawable.OVAL
                    color = ContextCompat.getColorStateList(context, theme.colorRes)
                }

                icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_check)
                icon.alpha = 0
                iconPadding = 0
                iconGravity = MaterialButton.ICON_GRAVITY_TEXT_TOP

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
                child.icon.alpha = 0
            }
        }
        button.icon.alpha = 255
        selectedColor = button.tag.toString()
    }

    companion object {
        const val TAG = "ColorDialog"
    }
}
