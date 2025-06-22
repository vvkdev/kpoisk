package com.vvkdev.presentation.fragments

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.vvkdev.domain.model.Film
import com.vvkdev.presentation.R
import com.vvkdev.presentation.base.state.BaseStateFragment
import com.vvkdev.presentation.databinding.FragmentFilmDetailsBinding
import com.vvkdev.presentation.viewmodels.FilmDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class FilmDetailsFragment :
    BaseStateFragment<FragmentFilmDetailsBinding, Film>(FragmentFilmDetailsBinding::inflate) {

    override val viewModel: FilmDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (resources.configuration.fontScale > FONT_SCALE_THRESHOLD) {
            binding.idLinearLayout.orientation = LinearLayout.VERTICAL
            binding.timeLinearLayout.orientation = LinearLayout.VERTICAL
        }
    }

    override fun onSuccess(film: Film) {
        with(binding) {
            idTextView.text = getString(R.string.id, film.id)
            updatedTextView.text = getString(R.string.updated, film.updated)
            nameTextView.text = film.name
            foreignNameTextView.text = film.foreignName
            foreignNameTextView.isGone = film.foreignName.isBlank()
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
            descriptionTextView.isGone = film.description.isBlank()
            descriptionLine.visibility = descriptionTextView.visibility
        }

    }

    companion object {
        private const val FONT_SCALE_THRESHOLD = 1.3f
    }
}
