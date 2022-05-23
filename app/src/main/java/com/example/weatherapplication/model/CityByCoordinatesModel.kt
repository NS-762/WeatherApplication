package com.example.weatherapplication.model

import com.example.weatherapplication.data.api.model.response.CityByCoordinatesResponse

/**
 * @author Kalmykova Natalia
 */
data class CityByCoordinatesModel(
    val localNames: LocalNamesModel?
)

data class LocalNamesModel(
    val ru: String?
)

val CityByCoordinatesResponse.model: CityByCoordinatesModel
    get() {
        return CityByCoordinatesModel(localNames.model)
    }

val CityByCoordinatesResponse.LocalNamesResponse.model: LocalNamesModel
    get() {
        return LocalNamesModel(ru)
    }
