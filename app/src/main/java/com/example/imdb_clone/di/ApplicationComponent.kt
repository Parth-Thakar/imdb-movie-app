package com.example.imdb_clone.di

import android.content.Context
import com.example.imdb_clone.ui.activities.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

//Component class i.e. connector component of Dagger to connect the consumer class and the provider and module class.
@Singleton
@Component(modules = [NetworkModule::class, DataBaseModule::class] )
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context) : ApplicationComponent
    }
}