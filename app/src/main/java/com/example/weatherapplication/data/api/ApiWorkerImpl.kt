package com.example.weatherapplication.data.api

import android.content.Context
import com.example.weatherapplication.R
import com.example.weatherapplication.data.api.model.response.CityByCoordinatesResponse
import com.example.weatherapplication.data.api.model.response.WeatherResponse
import com.example.weatherapplication.model.CityModel

/**
 * @author Kalmykova Natalia
 */
class ApiWorkerImpl(private val api: Api, private val context: Context) {

    suspend fun loadWeatherByCityName(
        city: String,
        count: Int,
        units: String,
        keyApi: String
    ): WeatherResponse? {
        val response = api.loadWeatherByCityName(city, count, units, keyApi)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun loadWeatherByCoordinates(
        lat: Double,
        lon: Double,
        count: Int,
        units: String,
        keyApi: String
    ): WeatherResponse? {
        val response = api.loadWeatherByCoordinates(lat, lon, count, units, keyApi)
        return if (response.isSuccessful) response.body() else null
    }

    /** Имитация обращению к серверу для получения списка всех городов */
    suspend fun loadCities(): List<CityModel> {
        val cities: MutableList<CityModel> = mutableListOf()
        context.resources.getStringArray(R.array.city_names).toList()
            .forEachIndexed { _, name ->
                cities.add(CityModel(name))
            }
        return cities
    }

    suspend fun getCityByCoordinates(
        lat: Double,
        lon: Double,
        limit: Int,
        keyApi: String
    ): List<CityByCoordinatesResponse>? {
        val response = api.getCityByCoordinates(lat, lon, limit, keyApi)
        return if (response.isSuccessful) response.body() else null
    }
}