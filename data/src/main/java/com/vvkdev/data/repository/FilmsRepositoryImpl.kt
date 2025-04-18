package com.vvkdev.data.repository

import com.vvkdev.data.remote.mapper.toFilm
import com.vvkdev.data.remote.model.ErrorResponse
import com.vvkdev.data.remote.service.FilmsService
import com.vvkdev.domain.LoadResult
import com.vvkdev.domain.model.Film
import com.vvkdev.domain.repository.FilmsRepository
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilmsRepositoryImpl @Inject constructor(
    private val filmService: FilmsService
) : FilmsRepository {

    override suspend fun getFilmById(id: Int): LoadResult<Film> {
        return try {
            val response = filmService.getFilmById(id)
            if (response.isSuccessful) {
                LoadResult.Success(response.body()!!.toFilm())
            } else {
                val errorBody = response.errorBody()?.string()
                val json = Json { ignoreUnknownKeys = true }
                val errors = errorBody?.let { json.decodeFromString<ErrorResponse>(it).messages }
                    ?: listOf("Empty error body")
                LoadResult.Error(errors.joinToString("\n"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e.message)
        }
    }
}
