package com.example.weatherapplication.ui.fragment.weather.di

import androidx.lifecycle.ViewModel
import com.example.weatherapplication.di.ViewModelKey
import com.example.weatherapplication.ui.fragment.weather.WeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author Kalmykova Natalia
 */
@Module
abstract class WeatherModule {
    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    abstract fun bindViewModel(viewModel: WeatherViewModel): ViewModel
}