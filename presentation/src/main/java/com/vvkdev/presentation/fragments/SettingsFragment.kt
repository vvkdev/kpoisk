package com.vvkdev.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vvkdev.presentation.base.BaseFragment
import com.vvkdev.presentation.databinding.FragmentSettingsBinding
import com.vvkdev.presentation.dialogs.ApiKeyDialog
import com.vvkdev.presentation.dialogs.ColorDialog

internal class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSettingsBinding =
        FragmentSettingsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.colorTextView.setOnClickListener {
            ColorDialog().show(childFragmentManager, ColorDialog.TAG)
        }

        binding.apikeyTextView.setOnClickListener {
            ApiKeyDialog().show(childFragmentManager, ApiKeyDialog.TAG)
        }
    }
}
