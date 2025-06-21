package com.vvkdev.presentation.fragments

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.navigation.fragment.findNavController
import com.vvkdev.presentation.base.BaseFragment
import com.vvkdev.presentation.databinding.FragmentFilmsBinding

class FilmsFragment : BaseFragment<FragmentFilmsBinding>(FragmentFilmsBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.openButton.setOnClickListener { openFilmDetails() }

        binding.filmIdEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                openFilmDetails()
                true
            } else {
                false
            }
        }
    }

    private fun openFilmDetails() {
        val filmId = binding.filmIdEditText.text.toString().toIntOrNull() ?: 0
        val direction =
            FilmsFragmentDirections.actionFilmsFragmentToFilmDetailsFragment(filmId)
        findNavController().navigate(direction)
    }
}
