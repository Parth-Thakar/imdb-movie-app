package com.example.imdb_clone.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.imdb_clone.models.Movie

@Database(entities = [Movie::class], version = 1)
abstract class MovDB : RoomDatabase() {
    // get the dao class
    abstract fun getMovDao() : MovDao
}