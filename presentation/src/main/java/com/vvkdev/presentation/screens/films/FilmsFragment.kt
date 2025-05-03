package com.vvkdev.presentation.screens.films

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.vvkdev.presentation.BaseFragment
import com.vvkdev.presentation.databinding.FragmentFilmsBinding

class FilmsFragment : BaseFragment<FragmentFilmsBinding>(FragmentFilmsBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            openButton.setOnClickListener {
                val filmId = filmIdEditText.text.toString().toIntOrNull() ?: 0
                val direction =
                    FilmsFragmentDirections.actionFilmsFragmentToFilmDetailsFragment(filmId)
                findNavController().navigate(direction)
            }
        }
    }

}
