package com.example.imdb_clone.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.imdb_clone.repository.MovRepo
import javax.inject.Inject

// Constructor injection to get the instance of product repo using Dagger.
class MainViewModelFactory @Inject constructor(private val repository: MovRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }

}