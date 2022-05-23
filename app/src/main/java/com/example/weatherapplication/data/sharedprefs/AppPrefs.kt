package com.example.weatherapplication.data.sharedprefs

import android.content.Context
import com.example.weatherapplication.R
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Kalmykova Natalia
 */
@Singleton
class AppPrefs @Inject constructor(private val context: Context) {

    private val prefs = context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)

    var cityName: String
        get() = prefs.getString(Key.CITY_NAME, null)
            ?: context.resources.getString(R.string.fragment_weather_default_city)
        set(value) = prefs.edit().putString(Key.CITY_NAME, value).apply()

    var latitude: Double
        get() = (prefs.getString(Key.LATITUDE, "0")?: "0").toDouble()
        set(value) = prefs.edit().putString(Key.LATITUDE, value.toString()).apply()

    var longitude: Double
        get() = (prefs.getString(Key.LONGITUDE, "0")?: "0").toDouble()
        set(value) = prefs.edit().putString(Key.LONGITUDE, value.toString()).apply()

    var amountDaysInForecast: Int
        get() = prefs.getInt(Key.AMOUNT_DAYS_IN_FORECAST, 7)
        set(value) = prefs.edit().putInt(Key.AMOUNT_DAYS_IN_FORECAST, value).apply()

    var temperatureUnits: String
        get() = prefs.getString(Key.TEMPERATURE_UNITS, null) ?: CELSIUS
        set(value) = prefs.edit().putString(Key.TEMPERATURE_UNITS, value).apply()

    var pressureUnits: String
        get() = prefs.getString(Key.PRESSURE_UNITS, null) ?: MILLIMETRE_OF_MERCURY
        set(value) = prefs.edit().putString(Key.PRESSURE_UNITS, value).apply()

    private object Key {
        const val CITY_NAME = "CITY_NAME"
        const val LATITUDE = "LATITUDE"
        const val LONGITUDE = "LONGITUDE"
        const val AMOUNT_DAYS_IN_FORECAST = "AMOUNT_DAYS_IN_FORECAST"
        const val TEMPERATURE_UNITS = "TEMPERATURE_UNITS"
        const val PRESSURE_UNITS = "PRESSURE_UNITS"
    }

    companion object {
        /** Наименование файла */
        private const val SHARED_PREFS_FILE_NAME = "AppPrefs"

        const val CELSIUS = "CELSIUS"
        const val MILLIMETRE_OF_MERCURY = "MILLIMETRE_OF_MERCURY"
    }
}