package com.example.weatherapplication.data.api

import com.example.weatherapplication.data.api.model.response.CityByCoordinatesResponse
import com.example.weatherapplication.data.api.model.response.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Kalmykova Natalia
 */
interface Api {

    @GET("data/2.5/forecast")
    suspend fun loadWeatherByCityName(
        @Query("q") city: String,
        @Query("cnt") count: Int,
        @Query("units") units: String,
        @Query("appid") keyApi: String
    ): Response<WeatherResponse>

    @GET("data/2.5/forecast")
    suspend fun loadWeatherByCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("cnt") count: Int,
        @Query("units") units: String,
        @Query("appid") keyApi: String
    ): Response<WeatherResponse>

    @GET("geo/1.0/reverse")
    suspend fun getCityByCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("limit") limit: Int,
        @Query("appid") keyApi: String
    ) : Response<List<CityByCoordinatesResponse>>
}