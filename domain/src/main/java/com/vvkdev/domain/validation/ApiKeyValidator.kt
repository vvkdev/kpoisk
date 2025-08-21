package com.vvkdev.domain.validation

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiKeyValidator @Inject constructor() : Validator<String> {

    override fun isValid(input: String): Boolean {
        val regex = Regex("^([A-Z0-9]{7}-){3}[A-Z0-9]{7}$")
        return regex.matches(input)
    }
}
