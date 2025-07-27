package com.vvkdev.presentation.dialogs

import android.app.Dialog
import android.graphics.drawable.GradientDrawable
import android.widget.GridLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import com.google.android.material.button.MaterialButton
import com.vvkdev.presentation.R
import com.vvkdev.presentation.base.BaseDialogFragment
import com.vvkdev.presentation.databinding.DialogContentColorBinding
import com.vvkdev.presentation.extensions.collectOnStarted
import com.vvkdev.presentation.viewmodels.ColorViewModel
import com.vvkdev.theme.AccentColor
import dagger.hilt.android.AndroidEntryPoint
import com.vvkdev.theme.R as ThemeR

@AndroidEntryPoint
class ColorDialog : BaseDialogFragment<DialogContentColorBinding>() {

    override val viewModel: ColorViewModel by viewModels()

    override val contentBindingBind = DialogContentColorBinding::bind
    override val contentLayoutRes = R.layout.dialog_content_color
    override val titleRes = R.string.select_color

    override fun onDialogCreated(dialog: Dialog) {
        collectOnStarted(viewModel.shouldRestart, this) {
            contentBinding.restartTextView.isInvisible = !it
        }
        setupColorButtons()
    }

    override fun onPositiveAction() {
        if (viewModel.shouldRestart.value) {
            viewModel.saveAccentColor()
            ActivityCompat.recreate(requireActivity())
        }
        dismiss()
    }

    private fun setupColorButtons() {
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

                setOnClickListener { button -> selectColor(button as MaterialButton) }
            }
            contentBinding.gridLayout.addView(button)
        }
    }

    private fun selectColor(button: MaterialButton) {
        contentBinding.gridLayout.children.forEach { view ->
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
