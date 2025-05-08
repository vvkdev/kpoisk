package com.vvkdev.presentation.fragments

import android.os.Bundle
import android.view.View
import com.vvkdev.presentation.BaseFragment
import com.vvkdev.presentation.databinding.FragmentSettingsBinding
import com.vvkdev.presentation.dialogs.ApiKeyDialog

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apiKeyButton.setOnClickListener {
            ApiKeyDialog().show(childFragmentManager, ApiKeyDialog.TAG)
        }
    }
}
