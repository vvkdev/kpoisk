package com.vvkdev.presentation.base.state

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import com.vvkdev.presentation.R
import com.vvkdev.presentation.base.BaseFragment
import com.vvkdev.presentation.databinding.StateLayoutBinding
import com.vvkdev.presentation.extensions.collectWhenStarted

abstract class BaseStateFragment<VB : ViewBinding, T>(
    inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB,
) : BaseFragment<VB>(inflate) {

    protected abstract val viewModel: BaseStateViewModel<T>

    protected abstract fun onSuccess(data: T)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val contentLayout = view.findViewById<View>(R.id.contentLayout)
        val stateBinding = StateLayoutBinding.bind(view)
        stateBinding.retryButton.setOnClickListener { viewModel.retry() }

        collectWhenStarted(viewModel.uiState) { state ->

            contentLayout.isGone = true
            stateBinding.progressBar.isGone = true
            stateBinding.errorLayout.isGone = true

            when (state) {
                is UiState.Default -> {}
                is UiState.Loading -> {
                    stateBinding.progressBar.isVisible = true
                }

                is UiState.Success -> {
                    onSuccess(state.data)
                    contentLayout.isVisible = true
                }

                is UiState.Error -> {
                    stateBinding.errorMessage.text =
                        state.message ?: getString(R.string.unknown_error)
                    stateBinding.errorLayout.isVisible = true
                }
            }
        }
    }
}
