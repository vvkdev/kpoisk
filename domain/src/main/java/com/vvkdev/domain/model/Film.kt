package com.vvkdev.domain.model

data class Film(
    val id: Int,
    val name: String,
    val foreignName: String,
    val rating: Float,
    val votes: Int = 0,
    val year: String,
    val description: String,
    val poster: String,
    val length: String,
    val has3D: Boolean,
    val genres: String,
    val countries: String,
    val updated: String,
)

data class FilmShort(
    val id: Int,
    val name: String,
    val foreignName: String,
    val rating: Float,
    val year: String = "-",
    val length: String = "-",
    val genres: String = "",
    val countries: String = "",
)
