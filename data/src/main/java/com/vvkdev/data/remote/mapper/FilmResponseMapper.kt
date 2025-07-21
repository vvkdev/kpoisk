package com.vvkdev.data.remote.mapper

import com.vvkdev.data.remote.model.FilmResponse
import com.vvkdev.data.remote.model.FilmYearsResponse
import com.vvkdev.domain.model.Film
import com.vvkdev.domain.model.FilmShort
import org.jetbrains.annotations.VisibleForTesting
import java.util.Date

fun FilmResponse.toFilm() = Film(
    id = id,
    name = name.orPlaceholderIfNullOrBlank("-"),
    foreignName = mapForeignName(alternativeName, enName),
    rating = rating?.kp ?: 0f,
    votes = votes?.kp ?: 0,
    year = mapYears(year, releaseYears),
    description = description.orEmptyIfNullOrBlank(),
    poster = poster?.previewUrl.orEmptyIfNullOrBlank(),
    length = totalSeriesLength?.toString() ?: movieLength?.toString() ?: "-",
    has3D = technology?.has3D ?: false,
    genres = listToBulletedString(genres) { genre -> genre.name },
    countries = listToBulletedString(countries) { country -> country.name },
    updated = Date().toIsoString(),
)

private fun mapForeignName(alt: String?, en: String?): String =
    alt?.takeIf { it.isNotBlank() } ?: en?.takeIf { it.isNotBlank() } ?: ""

@VisibleForTesting
internal fun mapYears(year: Int?, years: List<FilmYearsResponse?>?): String {
    val start = listOfNotNull(
        year?.takeIf { it > 0 },
        years?.firstOrNull()?.start?.takeIf { it > 0 },
    ).minOrNull()
    val end = years?.lastOrNull()?.end?.takeIf { it > 0 }

    return listOfNotNull(start, end)
        .joinToString("-")
        .orPlaceholderIfNullOrBlank("-")
}

@VisibleForTesting
internal fun <T> listToBulletedString(
    list: List<T?>?,
    transform: (T) -> String?,
): String = list
    ?.filterNotNull()
    ?.mapNotNull { item -> transform(item).takeUnless { it.isNullOrBlank() } }
    ?.joinToString(" • ")
    ?: ""
