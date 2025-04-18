package com.vvkdev.data.remote.mapper

import com.vvkdev.data.remote.model.FilmResponse
import com.vvkdev.data.remote.model.FilmYears
import com.vvkdev.domain.model.Film
import org.jetbrains.annotations.VisibleForTesting

fun FilmResponse.toFilm() = Film(
    id = id,
    name = name.toStringOrEmpty(),
    foreignName = parseForeignName(alternativeName, enName),
    rating = rating?.kp,
    votes = votes?.kp ?: 0,
    year = parseYears(year, releaseYears),
    description = description.toStringOrEmpty(),
    poster = poster?.previewUrl.toStringOrEmpty(),
    length = totalSeriesLength ?: movieLength,
    has3D = technology?.has3D ?: false,
    genres = mapListToString(genres) { genre -> genre.name },
    countries = mapListToString(countries) { country -> country.name },
)

private fun String?.toStringOrEmpty() = this?.takeIf { it.isNotBlank() } ?: ""

private fun parseForeignName(alt: String?, en: String?): String {
    return alt?.takeIf { it.isNotBlank() } ?: en ?: ""
}

@VisibleForTesting
internal fun parseYears(year: Int?, years: List<FilmYears?>?): String {
    val start = listOfNotNull(
        year?.takeIf { it > 0 },
        years?.firstOrNull()?.start?.takeIf { it > 0 },
    ).minOrNull()
    val end = years?.lastOrNull()?.end?.takeIf { it > 0 }

    return listOfNotNull(start, end).joinToString("-")
}

fun <T> mapListToString(list: List<T?>?, mapper: (T) -> String?): String {
    return list
        ?.filterNotNull()
        ?.mapNotNull { item -> mapper(item)?.takeIf { it.isNotBlank() } }
        ?.joinToString(" • ")
        ?: ""
}
