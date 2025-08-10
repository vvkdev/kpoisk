package com.vvkdev.domain.validation

internal interface Validator<T> {
    fun isValid(input: T): Boolean
}
