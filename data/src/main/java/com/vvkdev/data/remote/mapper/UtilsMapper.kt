package com.vvkdev.data.remote.mapper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal fun String?.orEmpty() = this?.takeIf { it.isNotBlank() } ?: ""

internal fun getNowAsString() =
    SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
