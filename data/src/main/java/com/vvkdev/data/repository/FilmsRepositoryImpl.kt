package com.vvkdev.data.repository

import com.vvkdev.data.local.dao.FilmDao
import com.vvkdev.data.local.mapper.toEntity
import com.vvkdev.data.local.mapper.toFilm
import com.vvkdev.data.remote.mapper.toFilm
import com.vvkdev.data.remote.model.ErrorResponse
import com.vvkdev.data.remote.service.FilmsService
import com.vvkdev.domain.LoadResult
import com.vvkdev.domain.model.Film
import com.vvkdev.domain.repository.FilmsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilmsRepositoryImpl @Inject constructor(
    private val filmsService: FilmsService,
    private val filmDao: FilmDao,
    private val json: Json,
) : FilmsRepository {

    override suspend fun getFilmById(id: Int): LoadResult<Film> {
        return filmDao.getById(id)?.let { LoadResult.Success(it.toFilm()) }
            ?: try {
                val response = filmsService.getFilmById(id)
                if (response.isSuccessful) {
                    val film = response.body()!!.toFilm()
                    CoroutineScope(Dispatchers.IO).launch { filmDao.insert(film.toEntity()) }
                    LoadResult.Success(film)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errors = errorBody
                        ?.let { json.decodeFromString<ErrorResponse>(it).messages }
                        ?: listOf("Empty error body")
                    LoadResult.Error(errors.joinToString("\n"))
                }
            } catch (e: Exception) {
                LoadResult.Error(e.message)
            }
    }
}
