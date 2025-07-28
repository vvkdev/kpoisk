package com.vvkdev.domain.repositories

import com.vvkdev.domain.LoadResult
import com.vvkdev.domain.models.Film
import com.vvkdev.domain.models.FilmShort

interface FilmRepository {
    suspend fun getFilmById(id: Int, forceRefresh: Boolean): LoadResult<Film>

    suspend fun findByName(name: String): LoadResult<List<FilmShort>>
}
