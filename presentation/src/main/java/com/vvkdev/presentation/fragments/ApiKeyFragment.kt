package com.vvkdev.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.vvkdev.presentation.BaseFragment
import com.vvkdev.presentation.R
import com.vvkdev.presentation.databinding.FragmentApiKeyBinding
import com.vvkdev.presentation.screens.apikey.ApiKeyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ApiKeyFragment :
    BaseFragment<FragmentApiKeyBinding>(FragmentApiKeyBinding::inflate) {

    private val viewModel: ApiKeyViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigateToFilms.collect {
                    findNavController().navigate(
                        R.id.action_apiKeyFragment_to_filmsFragment,
                        null,
                        navOptions { popUpTo(R.id.apiKeyFragment) { inclusive = true } }
                    )
                }
            }
        }

        binding.saveButton.setOnClickListener {
            viewModel.saveApiKey(binding.apiKeyEditText.text.toString())
        }
    }
}
