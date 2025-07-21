package com.vvkdev.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class FilmResponse(
    val id: Int,
    val name: String?,
    val alternativeName: String?,
    val enName: String?,
    val rating: FilmRatingResponse?,
    val votes: FilmVotesResponse?,
    val year: Int?,
    val releaseYears: List<FilmYearsResponse?>?,
    val description: String?,
    val poster: FilmPosterResponse?,
    val movieLength: Int?,
    val totalSeriesLength: Int?,
    val technology: FilmTechnologyResponse?,
    val genres: List<GenreResponse?>?,
    val countries: List<CountryResponse?>?,
)

@Serializable
data class FilmRatingResponse(
    val kp: Float?,
)

@Serializable
data class FilmVotesResponse(
    val kp: Int?,
)

@Serializable
data class FilmYearsResponse(
    val start: Int?,
    val end: Int?,
)

@Serializable
data class FilmPosterResponse(
    val previewUrl: String?,
)

@Serializable
data class FilmTechnologyResponse(
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
