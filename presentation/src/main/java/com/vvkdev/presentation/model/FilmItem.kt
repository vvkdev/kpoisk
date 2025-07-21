package com.vvkdev.presentation.model

data class FilmItem(
    val id: Int,
    val name: String,
    val foreignName: String,
    val rating: String,
    val time: String,
    val genres: String,
    val countries: String,
)
