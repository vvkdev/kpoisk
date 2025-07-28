package com.vvkdev.data.remote.mappers

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal fun String?.orEmptyIfNullOrBlank() = this.takeUnless { it.isNullOrBlank() } ?: ""

internal fun String?.orPlaceholderIfNullOrBlank(placeholder: String) =
    this.takeUnless { it.isNullOrBlank() } ?: placeholder

internal fun Date.toIsoString(): String =
    SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(this)
