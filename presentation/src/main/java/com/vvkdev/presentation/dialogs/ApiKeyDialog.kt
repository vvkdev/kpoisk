package com.vvkdev.presentation.dialogs

import android.app.Dialog
import android.text.InputType
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.vvkdev.presentation.R
import com.vvkdev.presentation.base.BaseDialogFragment
import com.vvkdev.presentation.base.DialogConfig
import com.vvkdev.presentation.databinding.DialogContentApikeyBinding
import com.vvkdev.presentation.extensions.collectOnStarted
import com.vvkdev.presentation.extensions.onDoneAction
import com.vvkdev.presentation.viewmodels.ApiKeyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class ApiKeyDialog : BaseDialogFragment<DialogContentApikeyBinding>() {

    override val viewModel: ApiKeyViewModel by viewModels()

    override fun getDialogConfig() = DialogConfig(
        titleRes = R.string.enter_api_key,
        contentLayoutRes = R.layout.dialog_content_apikey,
        contentBindingBind = DialogContentApikeyBinding::bind,
    )

    override fun onPositiveAction() {
        viewModel.saveApiKey(contentBinding.apiKeyEditText.text.toString())
    }

    override fun onDialogCreated(dialog: Dialog) {
        collectOnStarted(viewModel.isErrorVisible, this) {
            contentBinding.formatTextView.isVisible = !it
            contentBinding.errorTextView.isVisible = it
        }

        dialog.setOnShowListener {
            contentBinding.apiKeyEditText.doAfterTextChanged {
                viewModel.setErrorVisibility(false)
                setCancelable(false)
            }
        }

        contentBinding.apiKeyEditText.setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS) // for multiline IME_ACTION_DONE working
        contentBinding.apiKeyEditText.onDoneAction { onPositiveAction() }
    }

    companion object {
        const val TAG = "ApiKeyDialog"
    }
}
