package com.example.weatherapplication.model

/**
 * @author Kalmykova Natalia
 */
data class WeatherModel(
    var weatherByDays: List<WeatherForDayModel> = listOf()
)

data class WeatherForDayModel(
    var mainDescription: String = "",
    var name: String = "",
    var date: String = "",
    var tempMax: String = "",
    var windSpeed: String = "",
    var pressure: String = "",
    var humidity: String = "",
    var weatherPicture: Int = 0,
)