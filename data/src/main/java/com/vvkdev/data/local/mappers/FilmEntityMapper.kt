package com.vvkdev.data.local.mappers

import com.vvkdev.data.local.models.FilmEntity
import com.vvkdev.domain.models.Film

fun Film.toFilmEntity() = FilmEntity(
    id,
    name,
    foreignName,
    rating,
    votes,
    year,
    description,
    poster,
    length,
    has3D,
    countries,
    genres,
    updated,
)

fun FilmEntity.toFilm() = Film(
    id,
    name,
    foreignName,
    rating,
    votes,
    year,
    description,
    poster,
    length,
    has3D,
    countries,
    genres,
    updated,
)
