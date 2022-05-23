package com.example.weatherapplication.ui.fragment.weather.interactor

import com.example.weatherapplication.BuildConfig
import com.example.weatherapplication.data.api.ApiWorkerImpl
import com.example.weatherapplication.data.api.mapper.WeatherMapper
import com.example.weatherapplication.model.WeatherModel
import javax.inject.Inject

/**
 * @author Kalmykova Natalia
 */
class WeatherInteractor @Inject constructor(
    private val apiWorker: ApiWorkerImpl,
    private val weatherMapper: WeatherMapper
) {

    suspend fun loadWeatherByCityName(
        city: String,
        count: Int,
        units: String,
        keyApi: String = BuildConfig.API_KEY
    ): WeatherModel? {
        val response = apiWorker.loadWeatherByCityName(city, count, units, keyApi)
        var result: WeatherModel? = null

        response?.let { result = weatherMapper.responseToModel(it) }
        return result
    }

    suspend fun loadWeatherByCoordinates(
        lat: Double,
        lon: Double,
        count: Int,
        units: String,
        keyApi: String = BuildConfig.API_KEY
    ): WeatherModel? {
        val response = apiWorker.loadWeatherByCoordinates(lat, lon, count, units, keyApi)
        var result: WeatherModel? = null

        response?.let { result = weatherMapper.responseToModel(it) }
        return result
    }
}