package com.example.weatherapplication.data.api.model.response

import com.google.gson.annotations.SerializedName

/**
 * @author Kalmykova Natalia
 */
data class CityByCoordinatesResponse(

    @SerializedName("local_names")
    val localNames: LocalNamesResponse
) {

    data class LocalNamesResponse(

        @SerializedName("ru")
        val ru: String
    )
}