package com.vvkdev.domain.repository

interface ApiKeyRepository {
    fun setApiKey(apiKey: String)
    fun getApiKey(): String?
}
