package com.vvkdev.presentation.dialogs

import AccentColor
import android.app.Dialog
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vvkdev.presentation.R
import com.vvkdev.presentation.databinding.DialogColorBinding
import com.vvkdev.presentation.viewmodels.ColorViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.vvkdev.theme.R as ThemeR

@AndroidEntryPoint
class ColorDialog : DialogFragment() {

    private val viewModel: ColorViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogColorBinding.inflate(layoutInflater)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.shouldRestart.collect {
                    binding.restartTextView.visibility = if (it) View.VISIBLE else View.INVISIBLE
                }
            }
        }

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
                if (viewModel.shouldRestart.value) {
                    viewModel.saveAccentColor()
                    ActivityCompat.recreate(requireActivity())
                }
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
                iconPadding = 0
                iconGravity = MaterialButton.ICON_GRAVITY_TEXT_TOP
                icon.alpha =
                    if (theme == viewModel.selectedColor) ALPHA_VISIBLE else ALPHA_INVISIBLE

                setOnClickListener { button -> selectColor(button as MaterialButton, binding) }
            }
            binding.gridLayout.addView(button)
        }
    }

    private fun selectColor(button: MaterialButton, binding: DialogColorBinding) {
        binding.gridLayout.children.forEach { view ->
            if (view is MaterialButton) view.icon.alpha = ALPHA_INVISIBLE
        }
        button.icon.alpha = ALPHA_VISIBLE
        viewModel.selectedColor = AccentColor.valueOf(button.tag.toString())
    }

    companion object {
        const val TAG = "ColorDialog"
        private const val ALPHA_INVISIBLE = 0
        private const val ALPHA_VISIBLE = 255
    }
}
