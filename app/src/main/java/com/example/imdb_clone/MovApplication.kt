package com.example.imdb_clone

import android.app.Application
import com.example.imdb_clone.di.ApplicationComponent
import com.example.imdb_clone.di.DaggerApplicationComponent

class MovApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}