package com.example.imdb_clone.retrofit

import com.example.imdb_clone.models.Movies
import retrofit2.Response
import retrofit2.http.GET

interface MovApi {

    // for the database
    @GET("1.json")
    suspend fun getMovDB() : Response<Movies>

    // for the remote api
    @GET("2.json")
    suspend fun getMovRE() : Response<Movies>

}