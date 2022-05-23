package com.example.weatherapplication.data.api.model.response

import com.google.gson.annotations.SerializedName

/**
 * @author Kalmykova Natalia
 */
data class WeatherResponse(

    @SerializedName("message")
    var message: String = "",

    @SerializedName("list")
    var weatherByDays: List<WeatherForDayResponse> = listOf(),
) {

    data class WeatherForDayResponse(
        @SerializedName("dt")
        var date: Long = 0,
        
        @SerializedName("main")
        var main: MainResponse = MainResponse(),
        
        @SerializedName("wind")
        var wind: WindResponse = WindResponse(),
        
        @SerializedName("weather")
        var weather: List<WeatherResponse> = listOf(),
    )
    
    data class WeatherResponse(
        @SerializedName("id")
        var id: Int = 0,

        @SerializedName("main")
        var mainDescription: String = "",
    )

    data class MainResponse(
        @SerializedName("temp_max")
        var tempMax: Double = 0.0,

        @SerializedName("pressure")
        var pressure: Int = 0,

        @SerializedName("humidity")
        var humidity: Int = 0,
    )

    data class WindResponse(
        @SerializedName("speed")
        var speed: Double = 0.0,
    )
}