package com.example.imdb_clone.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.imdb_clone.db.MovDB
import com.example.imdb_clone.models.Movies
import com.example.imdb_clone.retrofit.MovApi
import javax.inject.Inject

class MovRepo @Inject constructor(
    private val apiInterface: MovApi,
    private val movDB: MovDB,
    private val applicationContext: Context
)  {
    // live data for the data fetched from the Database
    private val movLiveDataDB = MutableLiveData<Movies>()
    val moviesDB: LiveData<Movies>
        get() = movLiveDataDB
    // live data for the data fetched from the Remote API
    private val movLiveDataRE = MutableLiveData<Movies>()
    val moviesRE: LiveData<Movies>
        get() = movLiveDataRE



    //function to fetch the data from the retrofit api interface
    suspend fun getMoviesRE()
    {
        val result = apiInterface.getMovRE()
        if (result.isSuccessful && result.body() != null) {
            movLiveDataRE.postValue(result.body())
        }
    }

    //function to fetch the data from the database itself.
    suspend fun getMoviesDB()
    {
        val dao = movDB.getMovDao()
        val resultDB = dao.getMov()
        if(resultDB.size!=0)
        {
            movLiveDataDB.postValue(Movies(resultDB))
        }
        else
        {
            val result = apiInterface.getMovDB()
            if (result.isSuccessful && result.body() != null) {
                val dao = movDB.getMovDao()
                dao.addMov(result.body()!!.Movie_List)
                movLiveDataDB.postValue(result.body())
            }
        }

    }

}