package com.vvkdev.presentation.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.vvkdev.domain.model.Film
import com.vvkdev.presentation.R
import com.vvkdev.presentation.UiState
import com.vvkdev.presentation.base.BaseFragment
import com.vvkdev.presentation.databinding.FragmentFilmDetailsBinding
import com.vvkdev.presentation.extensions.collectWhenStarted
import com.vvkdev.presentation.viewmodels.FilmDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class FilmDetailsFragment :
    BaseFragment<FragmentFilmDetailsBinding>(FragmentFilmDetailsBinding::inflate) {

    private val viewModel: FilmDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (resources.configuration.fontScale > FONT_SCALE_THRESHOLD) {
            binding.idLinearLayout.orientation = LinearLayout.VERTICAL
            binding.timeLinearLayout.orientation = LinearLayout.VERTICAL
        }

        collectWhenStarted(viewModel.state) { state ->
            when (state) {
                UiState.Default -> {}
                UiState.Loading -> showLoading()
                is UiState.Success -> showFilm(state.data)
                is UiState.Error -> showError(
                    state.message ?: getString(R.string.unknown_error)
                )
            }
        }

        binding.retryButton.setOnClickListener { viewModel.retry() }
    }

    private fun showLoading() {
        with(binding) {
            rootLayout.setChildrenVisibility(View.GONE)
            errorLayout.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun showFilm(film: Film) {
        with(binding) {
            rootLayout.setChildrenVisibility(View.VISIBLE)
            progressBar.visibility = View.GONE
            errorLayout.visibility = View.GONE
            idTextView.text = getString(R.string.id, film.id)
            updatedTextView.text = getString(R.string.updated, film.updated)
            nameTextView.text = film.name
            foreignNameTextView.text = film.foreignName
            foreignNameTextView.visibility =
                if (film.foreignName.isBlank()) View.GONE else View.VISIBLE

            timeTextView.text = getString(
                R.string.time,
                film.year,
                film.length?.toString() ?: getString(R.string.unknown_sign)
            )
            ratingTextView.text = getString(
                R.string.rating,
                String.format(Locale.getDefault(), "%.1f", film.rating),
                resources.getQuantityString(R.plurals.votes, film.votes, film.votes)
            )

            genresTextView.text = film.genres
            countriesTextView.text = film.countries
            has3dTextView.isVisible = film.has3D
            descriptionTextView.text = film.description
            descriptionTextView.visibility =
                if (film.description.isBlank()) View.GONE else View.VISIBLE
            descriptionLine.visibility = descriptionTextView.visibility
        }
    }

    private fun showError(message: String) {
        with(binding) {
            rootLayout.setChildrenVisibility(View.GONE)
            progressBar.visibility = View.GONE
            errorLayout.visibility = View.VISIBLE
            errorMessage.text = message
        }
    }

    private fun ViewGroup.setChildrenVisibility(visibility: Int) {
        for (i in 0 until childCount) {
            getChildAt(i).visibility = visibility
        }
    }

    companion object {
        private const val FONT_SCALE_THRESHOLD = 1.3f
    }
}
