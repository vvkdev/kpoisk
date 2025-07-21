package com.vvkdev.data.remote.service

import com.vvkdev.data.remote.model.FilmListResponse
import com.vvkdev.data.remote.model.FilmResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmService {
    @GET("movie/{id}")
    suspend fun getFilmById(
        @Path("id") id: Int,
    ): Response<FilmResponse>

    @GET("movie/search")
    suspend fun findByName(
        @Query("query") name: String,
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1,
    ): Response<FilmListResponse>
}
