package com.vvkdev.presentation.model

data class FilmDetails(
    val id: String,
    val name: String,
    val foreignName: String,
    val time: String,
    val rating: String,
    val genres: String,
    val countries: String,
    val has3D: Boolean,
    val description: String,
    val url: String,
    val updated: String,
)
