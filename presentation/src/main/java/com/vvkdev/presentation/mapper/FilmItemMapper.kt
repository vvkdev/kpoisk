package com.vvkdev.presentation.mapper

import android.content.res.Resources
import com.vvkdev.domain.model.FilmShort
import com.vvkdev.presentation.R
import com.vvkdev.presentation.model.FilmItem

internal fun FilmShort.toFilmItem(res: Resources) = FilmItem(
    id = id,
    name = name,
    foreignName = foreignName,
    rating = "%.1f".format(rating),
    time = res.getString(R.string.time, year, length),
    countries = countries,
    genres = genres,
)
