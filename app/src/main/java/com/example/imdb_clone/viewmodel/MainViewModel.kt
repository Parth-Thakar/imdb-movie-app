package com.example.imdb_clone.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imdb_clone.models.Movies
import com.example.imdb_clone.repository.MovRepo
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MovRepo) : ViewModel() {

    // declaring the exposed livedata to observe in the UI
    val movLiveDataDB: LiveData<Movies>
        get() = repository.moviesDB

    val movLiveDataRE: LiveData<Movies>
        get() = repository.moviesRE


    init {
        //launching the coroutines scopes to call the repository function to fetch the data from the database and remoteAPI
        viewModelScope.launch {
            repository.getMoviesDB()
        }
        viewModelScope.launch {
            repository.getMoviesRE()
        }
    }

}

