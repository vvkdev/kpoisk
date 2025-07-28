package com.vvkdev.presentation.mappers

import android.content.res.Resources
import com.vvkdev.domain.models.FilmShort
import com.vvkdev.presentation.R
import com.vvkdev.presentation.models.FilmItem

internal fun FilmShort.toFilmItem(res: Resources) = FilmItem(
    id = id,
    name = name,
    foreignName = foreignName,
    rating = "%.1f".format(rating),
    time = res.getString(R.string.time, year, length),
    countries = countries,
    genres = genres,
)
