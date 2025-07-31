package com.vvkdev.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import coil3.load
import coil3.request.crossfade
import com.vvkdev.presentation.R
import com.vvkdev.presentation.base.BaseFragment
import com.vvkdev.presentation.databinding.FragmentPosterBinding
import com.vvkdev.presentation.databinding.StateLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PosterFragment : BaseFragment<FragmentPosterBinding>() {

    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPosterBinding =
        FragmentPosterBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = PosterFragmentArgs.fromBundle(requireArguments())

        val stateBinding = StateLayoutBinding.bind(binding.root)
        stateBinding.retryButton.setOnClickListener { loadPoster(args.posterUrl, stateBinding) }

        binding.backTitleBar.titleTextView.text = args.filmName
        binding.backTitleBar.backImageButton.setOnClickListener { findNavController().navigateUp() }
        loadPoster(args.posterUrl, stateBinding)
    }

    private fun loadPoster(url: String, stateBinding: StateLayoutBinding) {
        binding.posterImageView.load(url) {
            crossfade(true)
            listener(
                onStart = {
                    binding.successLayout.isInvisible = true
                    stateBinding.errorLayout.isGone = true
                    stateBinding.progressBar.isVisible = true
                },
                onSuccess = { _, _ ->
                    stateBinding.progressBar.isGone = true
                    binding.successLayout.isVisible = true
                },
                onError = { request, errorResult ->
                    stateBinding.errorMessage.text = errorResult.throwable.message
                        ?: getString(R.string.unknown_error)
                    stateBinding.progressBar.isGone = true
                    stateBinding.errorLayout.isVisible = true
                },
            )
        }
    }
}
