package com.example.weatherapplication.ui.fragment.weather.di

import com.example.weatherapplication.ui.fragment.weather.WeatherFragment
import dagger.Subcomponent

/**
 * @author Kalmykova Natalia
 */
@Subcomponent(modules = [WeatherModule::class])
interface WeatherComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): WeatherComponent
    }

    fun inject(fragment: WeatherFragment)
}