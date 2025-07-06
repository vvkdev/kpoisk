package com.vvkdev.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.vvkdev.presentation.base.BaseFragment
import com.vvkdev.presentation.databinding.FragmentFilmsBinding
import com.vvkdev.presentation.extensions.setOnDoneAction

class FilmsFragment : BaseFragment<FragmentFilmsBinding>(FragmentFilmsBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.openButton.setOnClickListener { openFilm() }
        binding.filmIdEditText.setOnDoneAction { openFilm() }
    }

    private fun openFilm() {
        val filmId = binding.filmIdEditText.text.toString().toIntOrNull() ?: 0
        val direction =
            FilmsFragmentDirections.actionFilmsFragmentToFilmFragment(filmId)
        findNavController().navigate(direction)
    }
}
