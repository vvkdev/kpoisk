package com.vvkdev.domain.repository

interface SettingsRepository {
    fun setColorAccent(color: String)
    fun getColorAccent(): String
}
