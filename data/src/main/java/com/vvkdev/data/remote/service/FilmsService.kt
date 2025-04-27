package com.vvkdev.data.remote.service

import com.vvkdev.data.remote.model.FilmResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface FilmsService {
    @GET("movie/{id}")
    suspend fun getFilmById(
        @Path("id") id: Int,
        @Header("X-API-KEY") apiKey: String = "6YJN4DK-WQX4NC2-PZNHYHE-XY4KHAC"
    ): Response<FilmResponse>
}
