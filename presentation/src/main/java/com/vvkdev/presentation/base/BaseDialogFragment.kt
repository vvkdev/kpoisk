package com.vvkdev.presentation.base

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vvkdev.presentation.R
import com.vvkdev.presentation.databinding.BaseDialogLayoutBinding
import com.vvkdev.presentation.extensions.collectOnStarted
import com.vvkdev.presentation.extensions.showShortToast

abstract class BaseDialogFragment<CVB : ViewBinding>() : DialogFragment() {

    protected abstract val viewModel: BaseViewModel

    protected abstract val contentBindingBind: (View) -> CVB
    protected abstract val contentLayoutRes: Int
    protected abstract val titleRes: Int
    protected open val positiveButtonTextRes: Int = R.string.save
    protected open val negativeButtonTextRes: Int = R.string.cancel
    protected open val neutralButtonTextRes: Int = 0

    protected abstract fun onDialogCreated(dialog: Dialog)
    protected abstract fun onPositiveAction()
    protected open fun onNegativeAction() = dismiss()
    protected open fun onNeutralAction() {}

    private var _binding: BaseDialogLayoutBinding? = null
    protected val binding get() = _binding!!

    private var _contentBinding: CVB? = null
    protected val contentBinding get() = _contentBinding!!

    final override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = BaseDialogLayoutBinding.inflate(layoutInflater)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(titleRes)
            .setView(binding.root)
            .create()

        binding.contentLayout.apply {
            layoutResource = contentLayoutRes
            setOnInflateListener { _, inflated -> _contentBinding = contentBindingBind(inflated) }
            inflate()
        }

        setupButtons()
        onDialogCreated(dialog)

        collectOnStarted(viewModel.effectFlow, this) { effect ->
            when (effect) {
                is UiEffect.ShowShortToast -> showShortToast(effect.messageRes)
                is DialogUiEffect.Dismiss -> dismiss()
            }
        }

        return dialog
    }

    override fun onDestroyView() {
        _contentBinding = null
        super.onDestroyView()
        _binding = null
    }

    private fun setupButtons() {
        binding.buttons.positiveButton.text = getString(positiveButtonTextRes)
        binding.buttons.positiveButton.setOnClickListener { onPositiveAction() }
        binding.buttons.negativeButton.text = getString(negativeButtonTextRes)
        binding.buttons.negativeButton.setOnClickListener { onNegativeAction() }
        if (neutralButtonTextRes != 0) {
            binding.buttons.neutralButton.isVisible = true
            binding.buttons.neutralButton.text = getString(neutralButtonTextRes)
            binding.buttons.neutralButton.setOnClickListener { onNeutralAction() }
        }
    }
}
