package com.example.weatherapplication.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapplication.database.entity.CityEntity

/**
 * @author Kalmykova Natalia
 */
@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: CityEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entities: List<CityEntity>)

    @Query("SELECT * FROM cities")
    suspend fun getAll(): List<CityEntity>

    @Query("DELETE FROM cities")
    suspend fun deleteAll()
}