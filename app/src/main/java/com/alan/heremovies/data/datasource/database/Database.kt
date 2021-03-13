package com.alan.heremovies.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alan.heremovies.data.datasource.database.dao.MoviesDao
import com.alan.heremovies.data.models.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

}