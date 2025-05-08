package com.vvkdev.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.vvkdev.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    // api key methods
    companion object {
        private val API_KEY = stringPreferencesKey("api_key")
    }

    override suspend fun saveApiKey(apiKey: String) {
        dataStore.edit { it[API_KEY] = apiKey }
    }

    override suspend fun getApiKey(): String? {
        return dataStore.data.first()[API_KEY]
    }

}
