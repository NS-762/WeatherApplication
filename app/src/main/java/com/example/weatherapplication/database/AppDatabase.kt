package com.example.weatherapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapplication.database.dao.CityDao
import com.example.weatherapplication.database.entity.CityEntity

/**
 * @author Kalmykova Natalia
 */
@Database(entities = [CityEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
}