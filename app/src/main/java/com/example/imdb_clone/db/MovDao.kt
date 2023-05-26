package com.example.imdb_clone.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.imdb_clone.models.Movie

@Dao
interface MovDao {

    // insert function to movie RoomDB entity
    //something new
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMov(list : List<Movie>)

    // get list data function to movie RoomDB entity
    @Query("SELECT * FROM Movie")
    suspend fun getMov() : List<Movie>

}