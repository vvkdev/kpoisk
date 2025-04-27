package com.vvkdev.domain.repository

interface SettingsRepository {

    // api key methods
    suspend fun saveApiKey(apiKey: String)
    suspend fun getApiKey(): String?
    suspend fun clearApiKey()
}
