package com.vvkdev.domain

sealed class LoadResult<out T> {
    data class Success<out T>(val data: T) : LoadResult<T>()
    data class Error(val message: String? = null) : LoadResult<Nothing>()
}
