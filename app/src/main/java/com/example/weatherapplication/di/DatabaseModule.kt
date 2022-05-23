package com.example.weatherapplication.di

import android.content.Context
import androidx.room.Room
import com.example.weatherapplication.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Kalmykova Natalia
 */
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun database(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    companion object {
        private const val DB_NAME = "weatherApplication.db"
    }
}