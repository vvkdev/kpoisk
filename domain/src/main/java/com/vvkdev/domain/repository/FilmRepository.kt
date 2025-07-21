package com.vvkdev.domain.repository

import com.vvkdev.domain.LoadResult
import com.vvkdev.domain.model.Film
import com.vvkdev.domain.model.FilmShort

interface FilmRepository {
    suspend fun getFilmById(id: Int, forceRefresh: Boolean): LoadResult<Film>

    suspend fun findByName(name: String): LoadResult<List<FilmShort>>
}
