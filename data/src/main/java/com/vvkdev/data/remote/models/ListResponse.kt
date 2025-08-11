package com.vvkdev.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class ListResponse<T>(
    val docs: List<T>,
    val total: Int?,
    val limit: Int?,
    val page: Int?,
    val pages: Int?,
)
