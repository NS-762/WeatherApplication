package com.example.weatherapplication.ui.fragment.weather

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapplication.R
import com.example.weatherapplication.data.sharedprefs.AppPrefs
import com.example.weatherapplication.model.WeatherModel
import com.example.weatherapplication.ui.base.BaseViewModel
import com.example.weatherapplication.ui.fragment.weather.interactor.WeatherInteractor
import javax.inject.Inject

/**
 * @author Kalmykova Natalia
 */
class WeatherViewModel @Inject constructor(
    context: Context,
    private val interactor: WeatherInteractor,
    private val appPrefs: AppPrefs
) : BaseViewModel() {

    private val resources = context.resources

    private val _weatherLiveData = MutableLiveData<WeatherModel>()
    val weatherLiveData: LiveData<WeatherModel> = _weatherLiveData

    var cityName = ""
    private var latitude = 0.0
    private var longitude = 0.0
    private var amountDaysInForecast = 0
    private var temperatureAndWindSpeedUnits = ""
    var pressureUnits = ""

    override fun onStart() {
        /* no-op */
    }

    fun loadData() {
        doWork {
            handleLoading(resources.getString(R.string.download_error)) {
                getSavedData()
                val result = if (cityName.isNullOrEmpty()) {
                    interactor.loadWeatherByCoordinates(
                        latitude,
                        longitude,
                        amountDaysInForecast,
                        temperatureAndWindSpeedUnits
                    )
                } else {
                    interactor.loadWeatherByCityName(
                        cityName,
                        amountDaysInForecast,
                        temperatureAndWindSpeedUnits
                    )
                }
                _weatherLiveData.postValue(result)
            }
        }
    }

    private fun getSavedData() {
        cityName = appPrefs.cityName
        latitude = appPrefs.latitude
        longitude = appPrefs.longitude
        amountDaysInForecast = appPrefs.amountDaysInForecast
        temperatureAndWindSpeedUnits = processTemperatureUnits(appPrefs.temperatureUnits)
        pressureUnits = processPressureUnits(appPrefs.pressureUnits)
    }

    /** Перевод сохраненных единиц измерения температуры в значение поля units для api */
    private fun processTemperatureUnits(temperatureUnits: String): String {
        return when (temperatureUnits) {
            CELSIUS -> METRIC
            else -> IMPERIAL
        }
    }

    /** Перевод сохраненных единиц измерения давления в значения для ui */
    private fun processPressureUnits(pressureUnits: String): String {
        return when (pressureUnits) {
            MILLIMETRE_OF_MERCURY -> resources.getString(R.string.fragment_settings_millimetre_of_mercury)
            else -> resources.getString(R.string.fragment_settings_hectopascal)
        }
    }

    /** Получить единицы измерения давления для ui */
    fun getWindSpeedUnits(): String {
        return when (temperatureAndWindSpeedUnits) {
            METRIC -> resources.getString(R.string.fragment_weather_meters_per_second)
            else -> resources.getString(R.string.fragment_weather_miles_per_hour)
        }
    }

    fun reloadData() {
        loadData()
    }

    companion object {
        const val CELSIUS = "CELSIUS"
        const val METRIC = "metric"
        const val IMPERIAL = "imperial"
        const val MILLIMETRE_OF_MERCURY = "MILLIMETRE_OF_MERCURY"
        const val NUMBER_THREE_HOUR_SEGMENTS_PER_DAY = 8
    }
}