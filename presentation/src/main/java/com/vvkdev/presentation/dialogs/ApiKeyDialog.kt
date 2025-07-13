package com.vvkdev.presentation.dialogs

import android.app.Dialog
import android.text.InputType
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.vvkdev.presentation.R
import com.vvkdev.presentation.base.BaseDialogFragment
import com.vvkdev.presentation.databinding.DialogContentApikeyBinding
import com.vvkdev.presentation.extensions.collectWhenStarted
import com.vvkdev.presentation.extensions.setOnDoneAction
import com.vvkdev.presentation.viewmodels.ApiKeyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApiKeyDialog : BaseDialogFragment<DialogContentApikeyBinding>() {

    override val viewModel: ApiKeyViewModel by viewModels()

    override val contentBindingBind = DialogContentApikeyBinding::bind
    override val contentLayoutRes = R.layout.dialog_content_apikey
    override val titleRes = R.string.enter_api_key

    override fun onDialogCreated(dialog: Dialog) {
        with(contentBinding) {
            collectWhenStarted(viewModel.showError) {
                formatTextView.isVisible = !it
                errorTextView.isVisible = it
            }

            dialog.setOnShowListener {
                apiKeyEditText.doAfterTextChanged {
                    viewModel.showError(false)
                    setCancelable(false)
                }
            }

            apiKeyEditText.setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS) // for multiline IME_ACTION_DONE working
            apiKeyEditText.setOnDoneAction { onPositiveAction() }
        }
    }

    override fun onPositiveAction() {
        viewModel.saveApiKey(contentBinding.apiKeyEditText.text.toString())
    }

    companion object {
        const val TAG = "ApiKeyDialog"
    }
}
