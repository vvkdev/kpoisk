package com.vvkdev.presentation.fragments

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.vvkdev.domain.model.Film
import com.vvkdev.presentation.R
import com.vvkdev.presentation.base.state.BaseStateFragment
import com.vvkdev.presentation.databinding.FragmentContentFilmDetailsBinding
import com.vvkdev.presentation.extensions.openInBrowser
import com.vvkdev.presentation.extensions.shareText
import com.vvkdev.presentation.mapper.toFilmDetails
import com.vvkdev.presentation.model.FilmDetails
import com.vvkdev.presentation.viewmodels.FilmDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilmDetailsFragment :
    BaseStateFragment<FragmentContentFilmDetailsBinding, Film, FilmDetails>() {

    override val viewModel: FilmDetailsViewModel by viewModels()

    override val contentBindingBind = FragmentContentFilmDetailsBinding::bind
    override val contentLayoutRes = R.layout.fragment_content_film_details

    override fun onContentViewCreated() {
        contentBinding.fab.setOnClickListener {
            contentBinding.fabShare.isVisible = !contentBinding.fabShare.isVisible
            contentBinding.fabWeb.isVisible = !contentBinding.fabWeb.isVisible
            contentBinding.fabPoster.isVisible = !contentBinding.fabPoster.isVisible
            contentBinding.fabRefresh.isVisible = !contentBinding.fabRefresh.isVisible
        }

        contentBinding.fabShare.setOnClickListener { shareText(uiModel.url) }
        contentBinding.fabWeb.setOnClickListener { openInBrowser(uiModel.url) }
        contentBinding.fabRefresh.setOnClickListener { viewModel.forceRefresh() }
    }

    override fun mapDomainModelToUiModel(domainModel: Film): FilmDetails =
        domainModel.toFilmDetails(resources)

    override fun fillContentViews(uiModel: FilmDetails) {
        val filmDetails = uiModel
        with(contentBinding) {
            idTextView.text = filmDetails.id
            updatedTextView.text = filmDetails.updated
            nameTextView.text = filmDetails.name
            foreignNameTextView.text = filmDetails.foreignName
            foreignNameTextView.isGone = filmDetails.foreignName.isBlank()
            timeTextView.text = filmDetails.time
            ratingTextView.text = filmDetails.rating
            genresTextView.text = filmDetails.genres
            genresTextView.isGone = filmDetails.genres.isBlank()
            countriesTextView.text = filmDetails.countries
            countriesTextView.isGone = filmDetails.countries.isBlank()
            countriesGenresDivider.isGone = genresTextView.isGone && countriesTextView.isGone
            has3dTextView.isVisible = filmDetails.has3D
            descriptionTextView.text = filmDetails.description
            descriptionTextView.isGone = filmDetails.description.isBlank()
            descriptionDivider.visibility = descriptionTextView.visibility
        }
    }
}
