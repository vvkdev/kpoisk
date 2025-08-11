package com.vvkdev.presentation.base

import android.app.Dialog
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vvkdev.presentation.databinding.BaseDialogLayoutBinding
import com.vvkdev.presentation.extensions.collectOnStarted
import com.vvkdev.presentation.extensions.showShortToast

internal abstract class BaseDialogFragment<CVB : ViewBinding>() : DialogFragment() {

    protected abstract val viewModel: BaseViewModel

    protected abstract fun getDialogConfig(): DialogConfig<CVB>
    protected abstract fun onPositiveAction()
    protected open fun onNegativeAction() = dismiss()
    protected open fun onNeutralAction() {}

    protected abstract fun onDialogCreated(dialog: Dialog)

    private var _binding: BaseDialogLayoutBinding? = null
    protected val binding get() = _binding!!

    private var _contentBinding: CVB? = null
    protected val contentBinding get() = _contentBinding!!

    final override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = BaseDialogLayoutBinding.inflate(layoutInflater)
        val config = getDialogConfig()

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(config.titleRes)
            .setView(binding.root)
            .create()

        binding.contentLayout.apply {
            layoutResource = config.contentLayoutRes
            setOnInflateListener { _, inflated ->
                _contentBinding = config.contentBindingBind(inflated)
            }
            inflate()
        }

        collectOnStarted(viewModel.effectFlow, this) { effect ->
            when (effect) {
                is UiEffect.ShowShortToast -> showShortToast(effect.messageRes)
                is DialogUiEffect.Dismiss -> dismiss()
            }
        }

        setupButtons(config)
        onDialogCreated(dialog)
        return dialog
    }

    override fun onDestroyView() {
        _contentBinding = null
        super.onDestroyView()
        _binding = null
    }

    private fun setupButtons(config: DialogConfig<CVB>) {
        with(binding) {
            buttons.positiveButton.text = getString(config.positiveButtonTextRes)
            buttons.positiveButton.setOnClickListener { onPositiveAction() }

            buttons.negativeButton.text = getString(config.negativeButtonTextRes)
            buttons.negativeButton.setOnClickListener { onNegativeAction() }

            if (config.neutralButtonTextRes != 0) {
                buttons.neutralButton.isVisible = true
                buttons.neutralButton.text = getString(config.neutralButtonTextRes)
                buttons.neutralButton.setOnClickListener { onNeutralAction() }
            }
        }
    }
}
