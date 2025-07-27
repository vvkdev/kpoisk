package com.vvkdev.presentation.extensions

import android.content.Intent
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.net.toUri
import androidx.core.view.children
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.vvkdev.presentation.R
import com.vvkdev.presentation.base.UiState
import com.vvkdev.presentation.databinding.StateLayoutBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

internal fun Fragment.showShortToast(@StringRes messageRes: Int) {
    Toast.makeText(requireContext(), messageRes, Toast.LENGTH_SHORT).show()
}

internal fun Fragment.shareText(text: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    startActivity(Intent.createChooser(intent, text))
}

internal fun Fragment.openInBrowser(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    startActivity(intent)
}

internal fun <T> Fragment.collectOnStarted(
    flow: Flow<T>,
    owner: LifecycleOwner,
    action: suspend (T) -> Unit,
) {
    owner.lifecycleScope.launch {
        owner.repeatOnLifecycle(Lifecycle.State.STARTED) { flow.collect(action) }
    }
}

internal fun <T> Fragment.collectUiState(
    uiState: Flow<UiState<T>>,
    rootViewGroup: ViewGroup,
    onRetry: () -> Unit,
    onSuccess: (T) -> Unit,
) {
    val stateBinding = StateLayoutBinding.bind(rootViewGroup)
    stateBinding.retryButton.setOnClickListener { onRetry() }

    collectOnStarted(uiState, viewLifecycleOwner) { state ->
        rootViewGroup.children.forEach { it.isGone = true }
        when (state) {
            is UiState.Default -> Unit
            is UiState.Loading -> stateBinding.progressBar.isVisible = true
            is UiState.Success -> onSuccess(state.data)
            is UiState.Error -> {
                stateBinding.errorMessage.text = state.message ?: getString(R.string.unknown_error)
                stateBinding.errorLayout.isVisible = true
            }
        }
    }
}
