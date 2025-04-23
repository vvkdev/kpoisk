package com.vvkdev.data.local.mapper

import com.vvkdev.data.local.model.FilmEntity
import com.vvkdev.domain.model.Film

fun Film.toEntity() = FilmEntity(
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
    genres,
    countries,
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
    genres,
    countries,
)