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
import com.vvkdev.presentation.databinding.BaseStateLayoutBinding
import com.vvkdev.presentation.extensions.collectWhenStarted
import kotlin.properties.Delegates

abstract class BaseStateFragment<CVB : ViewBinding, DM, UM : Any>(
    private val contentBindingBind: (View) -> CVB,
    @LayoutRes private val contentLayoutRes: Int,
) : BaseFragment<BaseStateLayoutBinding>(BaseStateLayoutBinding::inflate) {

    protected abstract val viewModel: BaseStateViewModel<DM>

    private var _contentBinding: CVB? = null
    protected val contentBinding get() = _contentBinding!!

    private var _uiModel: UM by Delegates.notNull()
    protected val uiModel get() = _uiModel

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStateObservers()
    }

    override fun onDestroyView() {
        _contentBinding = null
        super.onDestroyView()
    }

    protected abstract fun onContentViewCreated()
    protected abstract fun mapDomainModelToUiModel(domainModel: DM): UM
    protected abstract fun fillContentViews(uiModel: UM)

    private fun setStateObservers() {
        binding.retryButton.setOnClickListener { viewModel.retry() }

        collectWhenStarted(viewModel.uiState) { state ->
            binding.root.children.forEach { it.isGone = true }
            when (state) {
                is UiState.Default -> {}
                is UiState.Loading -> handleLoading()
                is UiState.Error -> handleError(state.message)
                is UiState.Success -> handleSuccess(state.data)
            }
        }
    }

    private fun handleLoading() {
        binding.progressBar.isVisible = true
    }

    private fun handleError(message: String?) {
        binding.errorMessage.text = message ?: getString(R.string.unknown_error)
        binding.errorLayout.isVisible = true
    }

    private fun handleSuccess(domainModel: DM) {
        if (_contentBinding == null) {
            binding.contentLayout.layoutResource = contentLayoutRes
            binding.contentLayout.setOnInflateListener { _, inflated ->
                _contentBinding = contentBindingBind(inflated)
            }
            binding.contentLayout.inflate()
        }
        _uiModel = mapDomainModelToUiModel(domainModel)
        fillContentViews(uiModel)
        onContentViewCreated()
        binding.contentLayout.isVisible = true
    }
}
