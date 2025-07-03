package com.vvkdev.presentation.fragments

import android.widget.LinearLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.vvkdev.domain.model.Film
import com.vvkdev.presentation.R
import com.vvkdev.presentation.base.state.BaseStateFragment
import com.vvkdev.presentation.databinding.ContentFilmDetailsBinding
import com.vvkdev.presentation.mapper.toFilmScreen
import com.vvkdev.presentation.viewmodels.FilmDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilmDetailsFragment : BaseStateFragment<ContentFilmDetailsBinding, Film>(
    ContentFilmDetailsBinding::bind,
    R.layout.content_film_details,
) {

    override val viewModel: FilmDetailsViewModel by viewModels()

    override fun onContentViewCreated() {
        if (resources.configuration.fontScale > FONT_SCALE_THRESHOLD) {
            contentBinding.idLinearLayout.orientation = LinearLayout.VERTICAL
            contentBinding.timeLinearLayout.orientation = LinearLayout.VERTICAL
        }

        contentBinding.fab.setOnClickListener {
            contentBinding.fabShare.isVisible = !contentBinding.fabShare.isVisible
            contentBinding.fabWeb.isVisible = !contentBinding.fabWeb.isVisible
            contentBinding.fabPoster.isVisible = !contentBinding.fabPoster.isVisible
            contentBinding.fabUpdate.isVisible = !contentBinding.fabUpdate.isVisible
        }
    }

    override fun fillContentViews(data: Film) {
        val filmScreen = data.toFilmScreen(resources)
        with(contentBinding) {
            idTextView.text = filmScreen.id
            updatedTextView.text = filmScreen.updated
            nameTextView.text = filmScreen.name
            foreignNameTextView.text = filmScreen.foreignName
            foreignNameTextView.isGone = filmScreen.foreignName.isBlank()
            timeTextView.text = filmScreen.time
            ratingTextView.text = filmScreen.rating
            genresTextView.text = filmScreen.genres
            genresTextView.isGone = filmScreen.genres.isBlank()
            countriesTextView.text = filmScreen.countries
            countriesTextView.isGone = filmScreen.countries.isBlank()
            genresCountriesLine.isGone = genresTextView.isGone && countriesTextView.isGone
            has3dTextView.isVisible = filmScreen.has3D
            descriptionTextView.text = filmScreen.description
            descriptionTextView.isGone = filmScreen.description.isBlank()
            descriptionLine.visibility = descriptionTextView.visibility
        }
    }

    companion object {
        private const val FONT_SCALE_THRESHOLD = 1.3f
    }
}
