package com.example.weatherapplication.database.repository

import com.example.weatherapplication.model.CityModel

/**
 * @author Kalmykova Natalia
 */
interface ViewedCityRepository {
    suspend fun insert(model: CityModel)

    suspend fun insert(models: List<CityModel>)

    suspend fun getAll(): List<CityModel>

    suspend fun deleteAll()
}