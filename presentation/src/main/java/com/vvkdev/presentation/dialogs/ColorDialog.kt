package com.vvkdev.presentation.dialogs

import AccentColor
import android.app.Dialog
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.GridLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vvkdev.presentation.R
import com.vvkdev.presentation.databinding.DialogColorBinding
import com.vvkdev.presentation.viewmodels.ColorViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.vvkdev.theme.R as ThemeR

@AndroidEntryPoint
class ColorDialog : DialogFragment() {

    private val viewModel: ColorViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogColorBinding.inflate(layoutInflater)

        setupColorButtons(binding)
        if (viewModel.showRestartMessage) {
            binding.textView.text = getString(R.string.app_will_be_restarted)
        }

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.select_color)
            .setView(binding.root)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.save, null)
            .create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener { dismiss() }
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                viewModel.saveAccentColor()
                ActivityCompat.recreate(requireActivity())
                dismiss()
            }
        }
        return dialog
    }

    private fun setupColorButtons(binding: DialogColorBinding) {
        val size = resources.getDimensionPixelSize(ThemeR.dimen.icon_medium)
        val margin = resources.getDimensionPixelSize(ThemeR.dimen.indent_medium)

        AccentColor.entries.forEach { theme ->
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
                icon.alpha = if (theme.name == viewModel.color) ALPHA_VISIBLE else ALPHA_INVISIBLE
                iconPadding = 0
                iconGravity = MaterialButton.ICON_GRAVITY_TEXT_TOP

                setOnClickListener { button -> selectColor(button as MaterialButton, binding) }
            }
            binding.gridLayout.addView(button)
        }
    }

    private fun selectColor(button: MaterialButton, binding: DialogColorBinding) {
        for (i in 0 until binding.gridLayout.childCount) {
            val child = binding.gridLayout.getChildAt(i)
            if (child is MaterialButton) {
                child.icon.alpha = ALPHA_INVISIBLE
            }
        }
        button.icon.alpha = ALPHA_VISIBLE
        viewModel.color = button.tag.toString()
        binding.textView.text = getString(R.string.app_will_be_restarted)
        viewModel.showRestartMessage = true
    }

    companion object {
        const val TAG = "ColorDialog"
        private const val ALPHA_INVISIBLE = 0
        private const val ALPHA_VISIBLE = 255
    }
}
