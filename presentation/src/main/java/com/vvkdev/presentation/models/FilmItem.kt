package com.vvkdev.presentation.models

data class FilmItem(
    val id: Int,
    val name: String,
    val foreignName: String,
    val rating: String,
    val time: String,
    val countries: String,
    val genres: String,
)
