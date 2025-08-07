package com.vvkdev.data.repositories

import com.vvkdev.core.AppDispatchers
import com.vvkdev.core.di.AppScope
import com.vvkdev.data.local.dao.FilmDao
import com.vvkdev.data.local.mappers.toFilm
import com.vvkdev.data.local.mappers.toFilmEntity
import com.vvkdev.data.remote.mappers.toFilm
import com.vvkdev.data.remote.mappers.toFilmShortList
import com.vvkdev.data.remote.services.FilmService
import com.vvkdev.data.repositories.base.BaseRepository
import com.vvkdev.domain.models.Film
import com.vvkdev.domain.models.FilmShort
import com.vvkdev.domain.repositories.FilmRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilmRepositoryImpl @Inject constructor(
    private val filmService: FilmService,
    private val filmDao: FilmDao,
    json: Json,
    @AppScope appScope: CoroutineScope,
    appDispatchers: AppDispatchers,
) : BaseRepository(json, appScope, appDispatchers), FilmRepository {

    override suspend fun getFilmById(id: Int, forceRefresh: Boolean): Result<Film> {
        val cached = if (!forceRefresh) filmDao.getById(id) else null
        return cached?.let { Result.success(it.toFilm()) }
            ?: safeApiCall { filmService.getFilmById(id) }
                .map { filmResponse ->
                    filmResponse.toFilm().also {
                        launchDbOperation { filmDao.insert(it.toFilmEntity()) }
                    }
                }
    }

    override suspend fun findByName(name: String): Result<List<FilmShort>> =
        safeApiCall { filmService.findByName(name) }.map { it.toFilmShortList() }
}
