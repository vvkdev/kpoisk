package com.vvkdev.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.vvkdev.domain.model.Film
import com.vvkdev.presentation.R
import com.vvkdev.presentation.base.BaseFragment
import com.vvkdev.presentation.base.UiState
import com.vvkdev.presentation.databinding.FragmentFilmDetailsBinding
import com.vvkdev.presentation.databinding.StateLayoutBinding
import com.vvkdev.presentation.extensions.collectOnStarted
import com.vvkdev.presentation.extensions.openInBrowser
import com.vvkdev.presentation.extensions.shareText
import com.vvkdev.presentation.mapper.toFilmDetails
import com.vvkdev.presentation.model.FilmDetails
import com.vvkdev.presentation.viewmodels.FilmDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilmDetailsFragment : BaseFragment<FragmentFilmDetailsBinding>() {

    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFilmDetailsBinding =
        FragmentFilmDetailsBinding::inflate

    private val viewModel: FilmDetailsViewModel by viewModels()

    private var filmDetails: FilmDetails = FilmDetails.empty()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val stateBinding = StateLayoutBinding.bind(binding.root)
        stateBinding.retryButton.setOnClickListener { viewModel.loadFilm(forceRefresh = true) }

        collectOnStarted(viewModel.uiState, viewLifecycleOwner) { state ->
            binding.root.children.forEach { it.isGone = true }
            when (state) {
                is UiState.Default -> Unit
                is UiState.Loading -> stateBinding.progressBar.isVisible = true
                is UiState.Success -> onSuccess(state.data)
                is UiState.Error -> {
                    stateBinding.errorMessage.text =
                        state.message ?: getString(R.string.unknown_error)
                    stateBinding.errorLayout.isVisible = true
                }
            }
        }

        binding.fab.setOnClickListener { onFabClick { } }
        binding.fabShare.setOnClickListener { onFabClick { shareText(filmDetails.url) } }
        binding.fabWeb.setOnClickListener { onFabClick { openInBrowser(filmDetails.url) } }
        binding.fabRefresh.setOnClickListener { onFabClick { viewModel.loadFilm(forceRefresh = true) } }
    }

    private fun onSuccess(data: Film) {
        filmDetails = data.toFilmDetails(resources)
        with(binding) {
            idTextView.text = filmDetails.id
            updatedTextView.text = filmDetails.updated
            nameTextView.text = filmDetails.name
            foreignNameTextView.text = filmDetails.foreignName
            foreignNameTextView.isGone = filmDetails.foreignName.isBlank()
            timeTextView.text = filmDetails.time
            ratingTextView.text = filmDetails.rating
            countriesTextView.text = filmDetails.countries
            countriesTextView.isGone = filmDetails.countries.isBlank()
            genresTextView.text = filmDetails.genres
            genresTextView.isGone = filmDetails.genres.isBlank()
            countriesGenresDivider.isGone = genresTextView.isGone && countriesTextView.isGone
            has3dTextView.isVisible = filmDetails.has3D
            descriptionTextView.text = filmDetails.description
            descriptionTextView.isGone = filmDetails.description.isBlank()
            descriptionDivider.visibility = descriptionTextView.visibility
            successLayout.isVisible = true
        }
    }

    private fun onFabClick(action: () -> Unit) {
        with(binding) {
            fabShare.isVisible = !fabShare.isVisible
            fabWeb.isVisible = !fabWeb.isVisible
            fabPoster.isVisible = !fabPoster.isVisible
            fabRefresh.isVisible = !fabRefresh.isVisible
        }
        action()
    }
}
