package com.vvkdev.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.vvkdev.presentation.R
import com.vvkdev.presentation.base.BaseFragment
import com.vvkdev.presentation.databinding.FragmentFilmSearchBinding
import com.vvkdev.presentation.extensions.doOnQueryChanged

internal class FilmSearchFragment : BaseFragment<FragmentFilmSearchBinding>() {

    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFilmSearchBinding =
        FragmentFilmSearchBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.doOnQueryChanged(onQuerySubmit = { query -> openFilmList(query) })
    }

    private fun openFilmList(filmName: String) {
        val navController = findNavController()
        if (navController.currentDestination?.id == R.id.filmSearchFragment) {
            navController.navigate(
                FilmSearchFragmentDirections.actionFilmSearchToFilmList(filmName)
            )
        }
    }
}
