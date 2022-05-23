package com.example.weatherapplication.database.repository

import com.example.weatherapplication.database.AppDatabase
import com.example.weatherapplication.database.entity.CityEntity
import com.example.weatherapplication.model.CityModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Kalmykova Natalia
 */
@Singleton
class ViewedCityRepositoryImpl @Inject constructor(database: AppDatabase) : ViewedCityRepository {

    private val dao = database.cityDao()

    override suspend fun insert(model: CityModel) {
        dao.insert(CityEntity(name = model.name))
    }

    override suspend fun insert(models: List<CityModel>) {
        dao.insert(models.map { CityEntity(it.name) })
    }

    override suspend fun getAll(): List<CityModel> {
        return dao.getAll().map { CityModel(it.name) }
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }
}