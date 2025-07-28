package com.vvkdev.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonPrimitive

@Serializable
data class ErrorResponse(
    @SerialName("message")
    private val _message: JsonElement? = null,
) {
    val messages: List<String>
        get() = when (_message) {
            null -> listOf("No messages")
            is JsonArray -> _message.map { it.jsonPrimitive.content }
            else -> listOf(_message.jsonPrimitive.content)
        }
}
