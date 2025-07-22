package com.vvkdev.data.local.mapper

import com.vvkdev.data.local.model.FilmEntity
import com.vvkdev.domain.model.Film

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
