package com.vvkdev.domain.repository

import com.vvkdev.domain.LoadResult
import com.vvkdev.domain.model.Film

interface FilmRepository {
    suspend fun getFilmById(id: Int, forceRefresh: Boolean): LoadResult<Film>
}
