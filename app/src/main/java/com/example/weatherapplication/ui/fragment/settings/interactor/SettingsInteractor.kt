package com.example.weatherapplication.ui.fragment.settings.interactor

import com.example.weatherapplication.data.sharedprefs.AppPrefs
import javax.inject.Inject

/**
 * @author Kalmykova Natalia
 */
class SettingsInteractor @Inject constructor(private val appPrefs: AppPrefs) {

    fun getTemperatureUnits(): String {
        return appPrefs.temperatureUnits
    }

    fun getPressureUnits(): String {
        return appPrefs.pressureUnits
    }

    fun getAmountDaysInForecast(): Int {
        return appPrefs.amountDaysInForecast
    }

    fun setTemperatureUnits(temperatureUnits: String) {
        appPrefs.temperatureUnits = temperatureUnits
    }

    fun setPressureUnits(pressureUnits: String) {
        appPrefs.pressureUnits = pressureUnits
    }

    fun setAmountDaysInForecast(amountDaysInForecast: Int) {
        appPrefs.amountDaysInForecast = amountDaysInForecast
    }
}