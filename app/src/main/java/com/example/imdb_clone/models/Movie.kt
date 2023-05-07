package com.example.imdb_clone.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

// data class of the movie which is the entity class for the roomDB too.

@Entity
data class Movie(
    val Cast: String,
    val Director: String,
    val Genres: String,
    @PrimaryKey(autoGenerate = false)
    val IMDBID: String,
    @SerializedName("Movie Poster")
    val Movie_Poster: String,
    val Rating: String,
    val Runtime: String,
    @SerializedName("Short Summary")
    val Short_Summary: String,
    val Summary: String,
    val Title: String,
    val Writers: String,
    val Year: String,
    @SerializedName("YouTube Trailer:")
    val YouTube_Trailer: String?

)