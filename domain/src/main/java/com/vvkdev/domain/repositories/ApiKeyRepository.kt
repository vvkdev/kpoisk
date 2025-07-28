package com.vvkdev.domain.repositories

interface ApiKeyRepository {
    fun setApiKey(apiKey: String)
    fun getApiKey(): String?
}
