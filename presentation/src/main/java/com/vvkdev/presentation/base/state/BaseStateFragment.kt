package com.vvkdev.presentation.base.state

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import com.vvkdev.presentation.R
import com.vvkdev.presentation.base.BaseFragment
import com.vvkdev.presentation.databinding.BaseStateLayoutBinding
import com.vvkdev.presentation.extensions.collectOnStarted
import kotlin.properties.Delegates

abstract class BaseStateFragment<CVB : ViewBinding, DM, UM : Any>() :
    BaseFragment<BaseStateLayoutBinding>() {

    protected abstract val viewModel: BaseStateViewModel<DM>

    protected abstract val contentBindingBind: (View) -> CVB
    protected abstract val contentLayoutRes: Int

    protected abstract fun mapDomainModelToUiModel(domainModel: DM): UM
    protected open fun setupListeners(uiModel: UM) {}
    protected abstract fun onSuccess(uiModel: UM)
    protected open fun onLoading() {}
    protected open fun onError() {}

    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> BaseStateLayoutBinding =
        BaseStateLayoutBinding::inflate

    private var _contentBinding: CVB? = null
    protected val contentBinding get() = _contentBinding!!

    private var _uiModel: UM by Delegates.notNull()
    protected val uiModel get() = _uiModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.retryButton.setOnClickListener { viewModel.retry() }

        collectOnStarted(viewModel.uiState, viewLifecycleOwner) { state ->
            binding.root.children.forEach { it.isGone = true }
            when (state) {
                is UiState.Default -> {}
                is UiState.Loading -> handleLoading()
                is UiState.Error -> handleError(state.message)
                is UiState.Success -> handleSuccess(state.data)
            }
        }
    }

    override fun onDestroyView() {
        _contentBinding = null
        super.onDestroyView()
    }

    private fun handleLoading() {
        onLoading()
        binding.progressBar.isVisible = true
    }

    private fun handleError(message: String?) {
        onError()
        binding.errorMessage.text = message ?: getString(R.string.unknown_error)
        binding.errorLayout.isVisible = true
    }

    private fun handleSuccess(domainModel: DM) {
        _uiModel = mapDomainModelToUiModel(domainModel)
        if (_contentBinding == null) {
            binding.contentLayout.layoutResource = contentLayoutRes
            binding.contentLayout.setOnInflateListener { _, inflated ->
                _contentBinding = contentBindingBind(inflated)
            }
            binding.contentLayout.inflate()
            setupListeners(uiModel)
        }
        onSuccess(uiModel)
        binding.contentLayout.isVisible = true
    }
}
