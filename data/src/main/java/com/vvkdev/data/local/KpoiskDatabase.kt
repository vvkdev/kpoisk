package com.vvkdev.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vvkdev.data.local.dao.FilmDao
import com.vvkdev.data.local.model.FilmEntity

@Database(entities = [FilmEntity::class], version = 1)
abstract class KpoiskDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}
