package com.example.weatherapplication.ui.fragment.choosecity.interactor

import com.example.weatherapplication.BuildConfig
import com.example.weatherapplication.data.api.ApiWorkerImpl
import com.example.weatherapplication.data.sharedprefs.AppPrefs
import com.example.weatherapplication.database.repository.ViewedCityRepository
import com.example.weatherapplication.model.CityByCoordinatesModel
import com.example.weatherapplication.model.CityModel
import com.example.weatherapplication.model.model
import javax.inject.Inject

/**
 * @author Kalmykova Natalia
 */
class ChooseCityInteractor @Inject constructor(
    private val apiWorker: ApiWorkerImpl,
    private val cityRepository: ViewedCityRepository,
    private val appPrefs: AppPrefs
) {

    suspend fun getAllCities(): List<CityModel> = apiWorker.loadCities()
    suspend fun getViewedCities(): List<CityModel> = cityRepository.getAll()

    suspend fun updateViewedCities(viewedCities: List<CityModel>) {
        cityRepository.deleteAll()
        cityRepository.insert(viewedCities)
    }

    fun updateChosenCity(chosenCity: CityModel) {
        appPrefs.cityName = chosenCity.name
    }

    suspend fun getCityByCoordinates(lat: Double, lon: Double): List<CityByCoordinatesModel>? {
        val response = apiWorker.getCityByCoordinates(lat, lon, LIMIT, BuildConfig.API_KEY)
        return response?.map { it.model }
    }

    companion object {
        private const val LIMIT = 1
    }
}