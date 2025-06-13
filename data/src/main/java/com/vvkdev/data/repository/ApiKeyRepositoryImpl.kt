package com.vvkdev.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.vvkdev.data.crypto.CryptoService
import com.vvkdev.domain.repository.ApiKeyRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiKeyRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val cryptoService: CryptoService,
) : ApiKeyRepository {

    @Volatile
    private var cachedKey: String? = null

    override fun setApiKey(apiKey: String) {
        sharedPreferences.edit { putString(API_KEY, cryptoService.encrypt(apiKey)) }
        cachedKey = apiKey
    }

    override fun getApiKey(): String? = cachedKey
        ?: sharedPreferences.getString(API_KEY, null)
            ?.let { encrypted -> cryptoService.decrypt(encrypted).also { cachedKey = it } }

    companion object {
        private const val API_KEY = "api_key"
    }
}
