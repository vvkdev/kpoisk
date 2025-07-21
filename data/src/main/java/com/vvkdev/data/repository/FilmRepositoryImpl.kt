package com.vvkdev.data.repository

import com.vvkdev.core.AppDispatchers
import com.vvkdev.core.extensions.childScope
import com.vvkdev.data.local.dao.FilmDao
import com.vvkdev.data.local.mapper.toFilm
import com.vvkdev.data.local.mapper.toFilmEntity
import com.vvkdev.data.parseErrorBody
import com.vvkdev.data.remote.mapper.toFilm
import com.vvkdev.data.remote.mapper.toFilmShortList
import com.vvkdev.data.remote.service.FilmService
import com.vvkdev.domain.LoadResult
import com.vvkdev.domain.model.Film
import com.vvkdev.domain.model.FilmShort
import com.vvkdev.domain.repository.FilmRepository
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

    override suspend fun getFilmById(id: Int, forceRefresh: Boolean): LoadResult<Film> {
        val cached = if (!forceRefresh) filmDao.getById(id) else null
        return cached
            ?.let { LoadResult.Success(it.toFilm()) }
            ?: try {
                val response = filmService.getFilmById(id)
                if (response.isSuccessful) {
                    val film = response.body()!!.toFilm()
                    LoadResult.Success(film).also {
                        appScope.childScope(appDispatchers.io).launch {
                            filmDao.insert(film.toFilmEntity())
                        }
                    }
                } else {
                    val errorMessage = parseErrorBody(response.errorBody(), json)
                    LoadResult.Error(errorMessage)
                }
            } catch (e: Exception) {
                LoadResult.Error(e.message)
            }
    }

    override suspend fun findByName(name: String): LoadResult<List<FilmShort>> {
        return try {
            val response = filmService.findByName(name)
            if (response.isSuccessful) {
                val films = response.body()!!.toFilmShortList()
                LoadResult.Success(films)
            } else {
                val errorMessage = parseErrorBody(response.errorBody(), json)
                LoadResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            LoadResult.Error(e.message)
        }
    }
}
