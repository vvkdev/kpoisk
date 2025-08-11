package com.vvkdev.domain.models

private interface FilmBase {
    val id: Int
    val name: String
    val foreignName: String
    val rating: Float
    val year: String
    val length: String
    val countries: String
    val genres: String
}

data class FilmShort(
    override val id: Int,
    override val name: String,
    override val foreignName: String,
    override val rating: Float,
    override val year: String = "-",
    override val length: String = "-",
    override val countries: String = "",
    override val genres: String = "",
) : FilmBase

data class Film(
    val short: FilmShort,
    val votes: Int,
    val description: String,
    val poster: String,
    val has3D: Boolean,
    val updated: String,
) : FilmBase by short
