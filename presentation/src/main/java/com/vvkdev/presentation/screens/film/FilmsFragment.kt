package com.vvkdev.presentation.screens.film

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.vvkdev.presentation.BaseFragment
import com.vvkdev.presentation.R
import com.vvkdev.presentation.databinding.FragmentFilmsBinding

class FilmsFragment : BaseFragment<FragmentFilmsBinding>(FragmentFilmsBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnOpen.setOnClickListener {
                val filmId = filmIdEditText.text.toString().toIntOrNull() ?: 0
                val args = Bundle().apply { putInt("film_id", filmId) }
                findNavController().navigate(R.id.action_filmsFragment_to_filmDetailsFragment, args)
            }
        }
    }

}
