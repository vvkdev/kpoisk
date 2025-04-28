package com.vvkdev.data.remote.mapper

internal fun String?.orEmpty() = this?.takeIf { it.isNotBlank() } ?: ""
