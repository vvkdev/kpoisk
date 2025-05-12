package com.vvkdev.domain.repository

interface ApiKeyRepository {
    suspend fun setApiKey(apiKey: String)
    suspend fun loadApiKeyToCache()
    fun getApiKey(): String?
}
