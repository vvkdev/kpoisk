package com.vvkdev.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.vvkdev.presentation.databinding.ActivityMainBinding
import com.vvkdev.presentation.dialogs.ApiKeyDialog
import com.vvkdev.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setTheme(viewModel.getAccentColor().themeRes)

        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainer) as NavHostFragment

        val navController = navHostFragment.navController
        binding.bottomNavView.setupWithNavController(navController)

        if (viewModel.getApiKey() == null) {
            ApiKeyDialog().show(supportFragmentManager, ApiKeyDialog.TAG)
        }
    }
}
