package com.vvkdev.data

import com.vvkdev.data.remote.models.ErrorResponse
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody

internal fun parseErrorBody(errorBody: ResponseBody?, json: Json): String =
    errorBody?.string()
        ?.let { json.decodeFromString<ErrorResponse>(it).messages }
        ?.joinToString("\n")
        ?: "Empty error body"
