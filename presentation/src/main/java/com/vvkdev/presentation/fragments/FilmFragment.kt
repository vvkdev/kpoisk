package com.vvkdev.presentation.fragments

import android.widget.LinearLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.vvkdev.domain.model.Film
import com.vvkdev.presentation.R
import com.vvkdev.presentation.base.state.BaseStateFragment
import com.vvkdev.presentation.databinding.FragmentContentFilmBinding
import com.vvkdev.presentation.extensions.openInBrowser
import com.vvkdev.presentation.extensions.shareText
import com.vvkdev.presentation.mapper.toFilmUi
import com.vvkdev.presentation.model.FilmUi
import com.vvkdev.presentation.viewmodels.FilmViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilmFragment : BaseStateFragment<FragmentContentFilmBinding, Film, FilmUi>() {

    override val viewModel: FilmViewModel by viewModels()

    override val contentBindingBind = FragmentContentFilmBinding::bind
    override val contentLayoutRes = R.layout.fragment_content_film

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

        contentBinding.fabShare.setOnClickListener { shareText(uiModel.url) }
        contentBinding.fabWeb.setOnClickListener { openInBrowser(uiModel.url) }
    }

    override fun mapDomainModelToUiModel(domainModel: Film): FilmUi =
        domainModel.toFilmUi(resources)

    override fun fillContentViews(uiModel: FilmUi) {
        val filmUi = uiModel
        with(contentBinding) {
            idTextView.text = filmUi.id
            updatedTextView.text = filmUi.updated
            nameTextView.text = filmUi.name
            foreignNameTextView.text = filmUi.foreignName
            foreignNameTextView.isGone = filmUi.foreignName.isBlank()
            timeTextView.text = filmUi.time
            ratingTextView.text = filmUi.rating
            genresTextView.text = filmUi.genres
            genresTextView.isGone = filmUi.genres.isBlank()
            countriesTextView.text = filmUi.countries
            countriesTextView.isGone = filmUi.countries.isBlank()
            genresCountriesLine.isGone = genresTextView.isGone && countriesTextView.isGone
            has3dTextView.isVisible = filmUi.has3D
            descriptionTextView.text = filmUi.description
            descriptionTextView.isGone = filmUi.description.isBlank()
            descriptionLine.visibility = descriptionTextView.visibility
        }
    }

    companion object {
        private const val FONT_SCALE_THRESHOLD = 1.3f
    }
}
