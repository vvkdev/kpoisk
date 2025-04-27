package com.vvkdev.data.remote.service

import com.vvkdev.data.remote.model.FilmResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FilmsService {
    @GET("movie/{id}")
    suspend fun getFilmById(
        @Path("id") id: Int,
    ): Response<FilmResponse>
}
