package com.vvkdev.data.remote.mapper

import com.vvkdev.data.remote.model.FilmResponse
import com.vvkdev.data.remote.model.FilmYears
import com.vvkdev.domain.model.Country
import com.vvkdev.domain.model.Film
import com.vvkdev.domain.model.Genre
import org.jetbrains.annotations.VisibleForTesting

fun FilmResponse.toFilm() = Film(
    id = id,
    name = name.toStringOrEmpty(),
    foreignName = parseForeignName(alternativeName, enName),
    rating = rating?.kp,
    votes = votes?.kp,
    year = parseYears(year, releaseYears),
    description = description.toStringOrEmpty(),
    poster = poster?.previewUrl.toStringOrEmpty(),
    length = totalSeriesLength ?: movieLength,
    has3D = technology?.has3D ?: false,
    genres = genres
        ?.filterNotNull()
        ?.mapNotNull { genre -> genre.name?.takeIf { it.isNotBlank() }?.let { Genre(it) } }
        ?: emptyList(),
    countries = countries
        ?.filterNotNull()
        ?.mapNotNull { country -> country.name?.takeIf { it.isNotBlank() }?.let { Country(it) } }
        ?: emptyList(),
)

private fun String?.toStringOrEmpty() = this?.takeIf { it.isNotBlank() } ?: ""

private fun parseForeignName(alt: String?, en: String?): String {
    return alt?.takeIf { it.isNotBlank() } ?: en ?: ""
}

private fun parseYears(year: Int?, years: List<FilmYears?>?): String {
    val start = listOfNotNull(
        year?.takeIf { it > 0 },
        years?.firstOrNull()?.start?.takeIf { it > 0 },
    ).minOrNull()
    val end = years?.lastOrNull()?.end?.takeIf { it > 0 }

    return listOfNotNull(start, end).joinToString("-")
}
