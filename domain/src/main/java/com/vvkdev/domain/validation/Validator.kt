package com.vvkdev.domain.validation

interface Validator<T> {
    fun isValid(input: T): Boolean
}
