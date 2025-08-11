package com.vvkdev.data.local.mappers

import com.vvkdev.data.local.models.FilmEntity
import com.vvkdev.domain.models.Film

internal fun Film.toFilmEntity() = FilmEntity(
    id = id,
    name = name,
    foreignName = foreignName,
    rating = rating,
    votes = votes,
    year = year,
    description = description,
    poster = poster,
    length = length,
    has3D = has3D,
    countries = countries,
    genres = genres,
    updated = updated,
)

internal fun FilmEntity.toFilm() = Film(
    id = id,
    name = name,
    foreignName = foreignName,
    rating = rating,
    votes = votes,
    year = year,
    description = description,
    poster = poster,
    length = length,
    has3D = has3D,
    countries = countries,
    genres = genres,
    updated = updated,
)
