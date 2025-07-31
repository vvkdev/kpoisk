package com.vvkdev.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vvkdev.domain.models.Film
import com.vvkdev.presentation.R
import com.vvkdev.presentation.base.BaseFragment
import com.vvkdev.presentation.databinding.FragmentFilmDetailsBinding
import com.vvkdev.presentation.extensions.collectUiState
import com.vvkdev.presentation.extensions.openInBrowser
import com.vvkdev.presentation.extensions.shareText
import com.vvkdev.presentation.mappers.toFilmDetails
import com.vvkdev.presentation.models.FilmDetails
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

        collectUiState(
            uiState = viewModel.uiState,
            rootViewGroup = binding.root,
            onRetry = { viewModel.loadFilm(forceRefresh = true) },
            onSuccess = { film -> onSuccess(film) },
        )

        binding.fab.setOnClickListener { onFabClick { } }
        binding.fabShare.setOnClickListener { onFabClick { shareText(filmDetails.url) } }
        binding.fabWeb.setOnClickListener { onFabClick { openInBrowser(filmDetails.url) } }
        binding.fabPoster.setOnClickListener {
            onFabClick { openPoster(filmDetails.name, filmDetails.poster) }
        }
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

    private fun openPoster(filmName: String, url: String) {
        val navController = findNavController()
        if (navController.currentDestination?.id == R.id.filmDetailsFragment) {
            navController.navigate(
                FilmDetailsFragmentDirections.actionFilmDetailsToPoster(filmName, url)
            )
        }
    }
}
