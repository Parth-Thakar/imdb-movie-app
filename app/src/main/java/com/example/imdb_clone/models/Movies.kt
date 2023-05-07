package com.example.imdb_clone.models

import com.google.gson.annotations.SerializedName

data class Movies(
    @SerializedName("Movie List")
    val Movie_List: List<Movie>
)