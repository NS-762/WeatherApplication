package com.example.weatherapplication.ui.fragment.settings

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.weatherapplication.R
import com.example.weatherapplication.ui.base.BaseViewModel
import com.example.weatherapplication.ui.fragment.settings.interactor.SettingsInteractor
import javax.inject.Inject

/**
 * @author Kalmykova Natalia
 */
class SettingsViewModel @Inject constructor(
    context: Context,
    private val interactor: SettingsInteractor
) : BaseViewModel() {

    private val resources = context.resources

    private var _temperatureUnitsLiveData = MutableLiveData<String>()
    var temperatureUnitsLiveData: MutableLiveData<String> = _temperatureUnitsLiveData

    private var _pressureUnitsLiveData = MutableLiveData<String>()
    var pressureUnitsLiveData: MutableLiveData<String> = _pressureUnitsLiveData

    private var _amountDaysInForecastLiveData = MutableLiveData<Int>()
    var amountDaysInForecastLiveData: MutableLiveData<Int> = _amountDaysInForecastLiveData

    override fun onStart() {
        loadData()
    }

    private fun loadData() {
        _temperatureUnitsLiveData.postValue(processTemperatureUnits(interactor.getTemperatureUnits()))
        _pressureUnitsLiveData.postValue(processPressuredUnits(interactor.getPressureUnits()))
        _amountDaysInForecastLiveData.postValue(interactor.getAmountDaysInForecast())
    }

    fun saveData(temperatureUnits: String, pressureUnits: String, amountDaysInForecast: Int) {
        interactor.setTemperatureUnits(processTemperatureUnits(temperatureUnits))
        interactor.setPressureUnits(processPressuredUnits(pressureUnits))
        interactor.setAmountDaysInForecast(amountDaysInForecast)
    }

    /** Перевод значений appPrefs и значений для кастомного свитча между друг другом
     * Например: "C" -> CELSIUS. CELSIUS -> "C"
     */
    private fun processTemperatureUnits(temperatureUnits: String): String {
        return when (temperatureUnits) {
            CELSIUS -> resources.getString(R.string.fragment_settings_celsius)
            FAHRENHEIT -> resources.getString(R.string.fragment_settings_fahrenheit)
            resources.getString(R.string.fragment_settings_celsius) -> CELSIUS
            else -> FAHRENHEIT
        }
    }

    /** Перевод значений appPrefs и значений для кастомного свитча между друг другом
     * Например: "мм.рт.ст" -> MILLIMETRE_OF_MERCURY. MILLIMETRE_OF_MERCURY -> "мм.рт.ст"
     */
    private fun processPressuredUnits(pressureUnits: String): String {
        return when (pressureUnits) {
            MILLIMETRE_OF_MERCURY -> resources.getString(R.string.fragment_settings_millimetre_of_mercury)
            HECTOPASCAL -> resources.getString(R.string.fragment_settings_hectopascal)
            resources.getString(R.string.fragment_settings_millimetre_of_mercury) -> MILLIMETRE_OF_MERCURY
            else -> HECTOPASCAL
        }
    }

    companion object {
        const val CELSIUS = "CELSIUS"
        const val FAHRENHEIT = "FAHRENHEIT"
        const val MILLIMETRE_OF_MERCURY = "MILLIMETRE_OF_MERCURY"
        const val HECTOPASCAL = "HECTOPASCAL"
    }
}

