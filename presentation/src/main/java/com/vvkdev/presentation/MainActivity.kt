package com.vvkdev.presentation

import AccentColor
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.vvkdev.domain.repository.ApiKeyRepository
import com.vvkdev.domain.repository.SettingsRepository
import com.vvkdev.presentation.databinding.ActivityMainBinding
import com.vvkdev.presentation.dialogs.ApiKeyDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var settingsRepository: SettingsRepository

    @Inject
    lateinit var apiKeyRepository: ApiKeyRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val color = settingsRepository.getColorAccent() ?: AccentColor.default().name
        setTheme(AccentColor.fromName(color).themeRes)

        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainer) as NavHostFragment

        val navController = navHostFragment.navController
        binding.bottomNavView.setupWithNavController(navController)

        lifecycleScope.launch {
            apiKeyRepository.loadApiKeyToCache()
            if (apiKeyRepository.getApiKey() == null) {
                ApiKeyDialog().show(supportFragmentManager, ApiKeyDialog.TAG)
            }
        }
    }
}
