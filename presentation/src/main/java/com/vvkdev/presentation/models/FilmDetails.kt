package com.vvkdev.presentation.models

data class FilmDetails(
    val id: String,
    val name: String,
    val foreignName: String,
    val time: String,
    val rating: String,
    val countries: String,
    val genres: String,
    val has3D: Boolean,
    val description: String,
    val poster: String,
    val url: String,
    val updated: String,
)
