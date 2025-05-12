package com.vvkdev.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.vvkdev.domain.repository.ApiKeyRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiKeyRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ApiKeyRepository {

    @Volatile
    private var cachedKey: String? = null

    override suspend fun setApiKey(apiKey: String) {
        dataStore.edit { it[API_KEY] = apiKey }
        cachedKey = apiKey
    }

    override suspend fun loadApiKeyToCache() {
        cachedKey = dataStore.data.first()[API_KEY]
    }

    override fun getApiKey(): String? = cachedKey

    companion object {
        private val API_KEY = stringPreferencesKey("api_key")
    }
}
