package com.vvkdev.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "films")
data class FilmEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val foreignName: String,
    val rating: Float,
    val votes: Int,
    val year: String,
    val description: String,
    val poster: String,
    val length: String,
    val has3D: Boolean,
    val countries: String,
    val genres: String,
    val updated: String,
)
