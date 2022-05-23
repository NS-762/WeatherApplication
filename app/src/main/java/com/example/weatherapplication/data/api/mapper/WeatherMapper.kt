package com.example.weatherapplication.data.api.mapper

import com.example.weatherapplication.R
import com.example.weatherapplication.data.api.model.response.WeatherResponse
import com.example.weatherapplication.data.sharedprefs.AppPrefs
import com.example.weatherapplication.model.WeatherForDayModel
import com.example.weatherapplication.model.WeatherModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * @author Kalmykova Natalia
 */
class WeatherMapper @Inject constructor(private val appPrefs: AppPrefs) {

    fun responseToModel(response: WeatherResponse): WeatherModel {
        var weatherByDays: MutableList<WeatherForDayModel> = mutableListOf()
        cheatsWithDate(response)

        response.weatherByDays.forEach {
            weatherByDays.add(convertWeatherForDayResponseToModel(it))
        }

        return WeatherModel(weatherByDays)
    }

    /** Прогноз выдается на каждые три часа пяти ближайших дней. Для красоты приложения переделаем,
     * будто прогноз для каждого отдельного дня
     */
    private fun cheatsWithDate(response: WeatherResponse) {
        var nextDate = Date().time
        response.weatherByDays.forEach {
            it.date = nextDate
            nextDate += DAY_IN_MILLISECONDS
        }
    }

    private fun convertWeatherForDayResponseToModel(response: WeatherResponse.WeatherForDayResponse): WeatherForDayModel {
        val weatherPicture = when (response.weather[0].mainDescription) {
            WeatherDescription.THUNDERSTORM.value -> R.drawable.thunderstorm
            WeatherDescription.DRIZZLE.value -> R.drawable.drizzle
            WeatherDescription.RAIN.value -> R.drawable.rain
            WeatherDescription.SHOW.value -> R.drawable.snow
            WeatherDescription.CLEAR.value -> R.drawable.clear
            WeatherDescription.CLOUDS.value -> R.drawable.clouds
            WeatherDescription.CYCLONE.value -> R.drawable.cyclone
            else -> R.drawable.clouds
        }

        return WeatherForDayModel(
            mainDescription = response.weather[0].mainDescription,
            date = SimpleDateFormat(
                "dd MMMM yyyy",
                Locale.getDefault()
            ).format(response.date),
            tempMax = "${response.main.tempMax.toInt()}\u00B0",
            windSpeed = response.wind.speed.toInt().toString(),
            pressure = processPressureByUnits(response.main.pressure).toString(),
            humidity = response.main.humidity.toString(),
            weatherPicture = weatherPicture
        )
    }

    /** Перевод значений гПа в мм.рт.ст, если это выборано в настройках */
    private fun processPressureByUnits(pressure: Int): Int {
        return if (appPrefs.pressureUnits == HECTOPASCAL) pressure else (pressure / COEFFICIENT).toInt()
    }

    enum class WeatherDescription(val value: String) {
        THUNDERSTORM("Thunderstorm"),
        DRIZZLE("Drizzle"),
        RAIN("Rain"),
        SHOW("Snow"),
        CLEAR("Clear"),
        CLOUDS("Clouds"),
        CYCLONE("Cyclone"),
    }

    companion object {
        private const val COEFFICIENT = 1.333
        private const val HECTOPASCAL = "HECTOPASCAL"
        private const val DAY_IN_MILLISECONDS = 24 * 60 * 60 * 1000
    }
}