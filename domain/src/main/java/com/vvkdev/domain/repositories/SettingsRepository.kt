package com.vvkdev.domain.repositories

interface SettingsRepository {
    fun setColorAccent(color: String)
    fun getColorAccent(): String?
}
