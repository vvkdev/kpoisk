package com.vvkdev.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vvkdev.data.local.models.FilmEntity

@Dao
interface FilmDao {
    @Query("SELECT * FROM films WHERE id = :id")
    suspend fun getById(id: Int): FilmEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(filmEntity: FilmEntity)
}
