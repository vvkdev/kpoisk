package com.vvkdev.presentation.model

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
    val url: String,
    val updated: String,
) {
    companion object {
        fun empty() = FilmDetails(
            id = "",
            name = "",
            foreignName = "",
            time = "",
            rating = "",
            countries = "",
            genres = "",
            has3D = false,
            description = "",
            url = "",
            updated = ""
        )
    }
}
