package com.vvkdev.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vvkdev.presentation.R
import com.vvkdev.presentation.adapters.FilmsAdapter
import com.vvkdev.presentation.base.BaseFragment
import com.vvkdev.presentation.databinding.FragmentFilmListBinding
import com.vvkdev.presentation.extensions.collectUiState
import com.vvkdev.presentation.mappers.toFilmItem
import com.vvkdev.presentation.viewmodels.FilmListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class FilmListFragment : BaseFragment<FragmentFilmListBinding>() {

    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFilmListBinding =
        FragmentFilmListBinding::inflate

    private val viewModel: FilmListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val filmsAdapter = FilmsAdapter { filmItem -> openFilmDetails(filmItem.id) }

        with(binding) {
            backTitleBar.titleTextView.text = getString(R.string.search_query, viewModel.filmName)
            backTitleBar.backImageButton.setOnClickListener { findNavController().navigateUp() }
            filmsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            filmsRecyclerView.adapter = filmsAdapter
        }

        collectUiState(
            uiState = viewModel.uiState,
            rootViewGroup = binding.root,
            onRetry = { viewModel.loadFilms() },
            onSuccess = { data ->
                filmsAdapter.items = data.map { it.toFilmItem(resources) }
                binding.successLayout.isVisible = true
            },
        )
    }

    private fun openFilmDetails(filmId: Int) {
        val direction = FilmListFragmentDirections.actionFilmListToFilmDetails(filmId)
        findNavController().navigate(direction)
    }
}
