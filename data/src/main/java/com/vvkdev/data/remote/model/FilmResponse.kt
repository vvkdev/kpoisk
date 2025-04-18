package com.vvkdev.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class FilmResponse(
    val id: Int,
    val name: String?,
    val alternativeName: String?,
    val enName: String?,
    val rating: FilmRating?,
    val votes: FilmVotes?,
    val year: Int?,
    val releaseYears: List<FilmYears?>?,
    val description: String?,
    val poster: FilmPoster?,
    val movieLength: Int?,
    val totalSeriesLength: Int?,
    val technology: FilmTechnology?,
    val genres: List<GenreResponse?>?,
    val countries: List<CountryResponse?>?,
)

@Serializable
data class FilmRating(
    val kp: Float?,
)

@Serializable
data class FilmVotes(
    val kp: Int?,
)

@Serializable
data class FilmYears(
    val start: Int?,
    val end: Int?,
)

@Serializable
data class FilmPoster(
    val previewUrl: String?,
)

@Serializable
data class FilmTechnology(
    val has3D: Boolean?,
)

@Serializable
data class GenreResponse(
    val name: String?,
)

@Serializable
data class CountryResponse(
    val name: String?,
)
