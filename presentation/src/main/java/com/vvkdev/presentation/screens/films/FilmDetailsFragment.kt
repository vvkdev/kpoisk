package com.vvkdev.presentation.screens.films

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.vvkdev.domain.model.Film
import com.vvkdev.presentation.BaseFragment
import com.vvkdev.presentation.R
import com.vvkdev.presentation.databinding.FragmentFilmDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class FilmDetailsFragment :
    BaseFragment<FragmentFilmDetailsBinding>(FragmentFilmDetailsBinding::inflate) {

    private val viewModel: FilmDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        FilmState.Loading -> showLoading()
                        is FilmState.Success -> showFilm(state.data)
                        is FilmState.Error -> showError(
                            state.message ?: getString(R.string.unknown_error)
                        )
                    }
                }
            }
        }

        binding.retryButton.setOnClickListener { viewModel.retry() }
    }

    private fun showLoading() {
        with(binding) {
            constraintLayout.setChildrenVisibility(View.GONE)
            errorLayout.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun showFilm(film: Film) {
        with(binding) {
            constraintLayout.setChildrenVisibility(View.VISIBLE)
            progressBar.visibility = View.GONE
            errorLayout.visibility = View.GONE
            nameTextView.text = film.name
            foreignNameTextView.text = film.foreignName
            descriptionTextView.text = film.description
            yearTextView.text = film.year
            lengthTextView.text = film.length?.toString() ?: getString(R.string.unknown_sign)
            ratingTextView.text = String.format(Locale.getDefault(), "%.1f", film.rating)
            votesTextView.text =
                resources.getQuantityString(R.plurals.votes, film.votes, film.votes)
            has3dImageView.isVisible = film.has3D
            genresTextView.text = film.genres
            countriesTextView.text = film.countries
            updatedTextView.text = film.updated
        }
    }

    private fun showError(message: String) {
        with(binding) {
            constraintLayout.setChildrenVisibility(View.GONE)
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

}
