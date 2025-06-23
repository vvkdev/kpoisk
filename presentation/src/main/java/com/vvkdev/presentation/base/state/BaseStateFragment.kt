package com.vvkdev.presentation.base.state

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.view.children
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import com.vvkdev.presentation.R
import com.vvkdev.presentation.base.BaseFragment
import com.vvkdev.presentation.databinding.StateLayoutBinding
import com.vvkdev.presentation.extensions.collectWhenStarted

abstract class BaseStateFragment<CVB : ViewBinding, D>(
    private val contentBindingBind: (View) -> CVB,
    @LayoutRes private val contentLayoutRes: Int,
) : BaseFragment<StateLayoutBinding>(StateLayoutBinding::inflate) {

    protected abstract val viewModel: BaseStateViewModel<D>

    private var _contentBinding: CVB? = null
    protected val contentBinding get() = _contentBinding!!

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStateObservers()
    }

    override fun onDestroyView() {
        _contentBinding = null
        super.onDestroyView()
    }

    protected abstract fun onContentViewCreated()
    protected abstract fun fillContentViews(data: D)

    private fun setStateObservers() {
        binding.retryButton.setOnClickListener { viewModel.retry() }

        collectWhenStarted(viewModel.uiState) { state ->
            binding.root.children.forEach { it.isGone = true }
            when (state) {
                is UiState.Default -> {}
                is UiState.Loading -> showLoading()
                is UiState.Error -> showError(state.message)
                is UiState.Success -> showContent(state.data)
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
    }

    private fun showError(message: String?) {
        binding.errorMessage.text = message ?: getString(R.string.unknown_error)
        binding.errorLayout.isVisible = true
    }

    private fun showContent(data: D) {
        if (_contentBinding == null) {
            binding.contentLayout.layoutResource = contentLayoutRes
            binding.contentLayout.setOnInflateListener { _, inflated ->
                _contentBinding = contentBindingBind(inflated)
                onContentViewCreated()
            }
            binding.contentLayout.inflate()
        }
        fillContentViews(data)
        binding.contentLayout.isVisible = true
    }
}
