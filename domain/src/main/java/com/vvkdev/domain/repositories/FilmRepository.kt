package com.vvkdev.domain.repositories

import com.vvkdev.domain.models.Film
import com.vvkdev.domain.models.FilmShort

interface FilmRepository {
    suspend fun getFilmById(id: Int, forceRefresh: Boolean): Result<Film>
    suspend fun findByName(name: String): Result<List<FilmShort>>
}
