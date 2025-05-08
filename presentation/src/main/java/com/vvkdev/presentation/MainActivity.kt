package com.vvkdev.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.vvkdev.domain.repository.SettingsRepository
import com.vvkdev.presentation.databinding.ActivityMainBinding
import com.vvkdev.presentation.dialogs.ApiKeyDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var settingsRepository: SettingsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainer) as NavHostFragment

        val navController = navHostFragment.navController
        binding.bottomNavView.setupWithNavController(navController)

        lifecycleScope.launch {
            if (settingsRepository.getApiKey() == null) {
                withContext(Dispatchers.Main) {
                    ApiKeyDialog().show(supportFragmentManager, ApiKeyDialog.TAG)
                }
            }
        }


    }
}
