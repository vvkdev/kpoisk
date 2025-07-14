package com.vvkdev.domain.repository

import com.vvkdev.domain.LoadResult
import com.vvkdev.domain.model.Film

interface FilmsRepository {
    suspend fun getFilmById(id: Int, forceRefresh: Boolean): LoadResult<Film>
}
