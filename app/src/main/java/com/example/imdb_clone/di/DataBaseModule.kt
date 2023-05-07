package com.example.imdb_clone.di

import android.content.Context
import androidx.room.Room
import com.example.imdb_clone.db.MovDB
import com.example.imdb_clone.db.MovDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module

class DataBaseModule {

    // Module class for returining the database instance
    @Singleton
    @Provides
    fun provideMovDB(context : Context) : MovDB{
        return Room.databaseBuilder(context,MovDB::class.java,"MovDB").build()
    }
}