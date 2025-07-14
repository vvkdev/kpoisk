package com.vvkdev.presentation.mapper

import android.content.res.Resources
import com.vvkdev.domain.model.Film
import com.vvkdev.presentation.R
import com.vvkdev.presentation.constants.Constants
import com.vvkdev.presentation.model.FilmUi
import java.util.Locale

internal fun Film.toFilmUi(res: Resources) = FilmUi(
    id = res.getString(R.string.id, id),
    name = name,
    foreignName = foreignName,
    time = res.getString(R.string.time, year, length),
    rating = res.getString(
        R.string.rating,
        String.format(Locale.getDefault(), "%.1f", rating),
        res.getQuantityString(R.plurals.votes, votes, votes)
    ),
    genres = genres,
    countries = countries,
    has3D = has3D,
    description = description,
    url = "${Constants.KP_BASE_URL}/film/$id",
    updated = res.getString(R.string.updated, updated),
)
