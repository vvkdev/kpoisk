package com.vvkdev.data.repositories

import com.vvkdev.core.AppDispatchers
import com.vvkdev.core.extensions.childScope
import com.vvkdev.data.local.dao.FilmDao
import com.vvkdev.data.local.mappers.toFilm
import com.vvkdev.data.local.mappers.toFilmEntity
import com.vvkdev.data.parseErrorBody
import com.vvkdev.data.remote.mappers.toFilm
import com.vvkdev.data.remote.mappers.toFilmShortList
import com.vvkdev.data.remote.services.FilmService
import com.vvkdev.domain.models.Film
import com.vvkdev.domain.models.FilmShort
import com.vvkdev.domain.repositories.FilmRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilmRepositoryImpl @Inject constructor(
    private val filmService: FilmService,
    private val filmDao: FilmDao,
    private val json: Json,
    private val appDispatchers: AppDispatchers,
    private val appScope: CoroutineScope,
) : FilmRepository {

    override suspend fun getFilmById(id: Int, forceRefresh: Boolean): Result<Film> {
        val cached = if (!forceRefresh) filmDao.getById(id) else null
        return cached
            ?.let { Result.success(it.toFilm()) }
            ?: try {
                val response = filmService.getFilmById(id)
                if (response.isSuccessful) {
                    val film = response.body()!!.toFilm()
                    Result.success(film).also {
                        appScope.childScope(appDispatchers.io).launch {
                            filmDao.insert(film.toFilmEntity())
                        }
                    }
                } else {
                    val errorMessage = parseErrorBody(response.errorBody(), json)
                    Result.failure(Exception(errorMessage))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
    }

    override suspend fun findByName(name: String): Result<List<FilmShort>> {
        return try {
            val response = filmService.findByName(name)
            if (response.isSuccessful) {
                val films = response.body()!!.toFilmShortList()
                Result.success(films)
            } else {
                val errorMessage = parseErrorBody(response.errorBody(), json)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
