package com.vvkdev.presentation.screens.film

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
import java.text.NumberFormat
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
        binding.constraintLayout.setChildrenVisibility(View.GONE)
        binding.errorLayout.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showFilm(film: Film) {
        binding.constraintLayout.setChildrenVisibility(View.VISIBLE)
        binding.progressBar.visibility = View.GONE
        binding.errorLayout.visibility = View.GONE
        with(binding) {
            nameTextView.text = film.name
            foreignNameTextView.text = film.foreignName
            descriptionTextView.text = film.description
            yearTextView.text = film.year
            lengthTextView.text = getString(R.string.length_in_minutes, film.length)
            ratingTextView.text =
                getString(R.string.votes, film.rating, film.votes?.formatNumGroups())
            has3dImageView.isVisible = film.has3D
            genresTextView.text = film.genres
            countriesTextView.text = film.countries
        }
    }

    private fun showError(message: String) {
        binding.constraintLayout.setChildrenVisibility(View.GONE)
        binding.progressBar.visibility = View.GONE
        binding.errorLayout.visibility = View.VISIBLE
        binding.errorMessage.text = message
    }

    private fun Int.formatNumGroups(): String =
        NumberFormat.getNumberInstance(Locale.getDefault()).format(this)

    private fun ViewGroup.setChildrenVisibility(visibility: Int) {
        for (i in 0 until childCount) {
            getChildAt(i).visibility = visibility
        }
    }

}
