package com.vvkdev.presentation.screens.film

import android.os.Bundle
import android.view.View
import com.vvkdev.presentation.BaseFragment
import com.vvkdev.presentation.databinding.FragmentFilmDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilmDetailsFragment :
    BaseFragment<FragmentFilmDetailsBinding>(FragmentFilmDetailsBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
