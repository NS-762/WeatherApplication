package com.example.weatherapplication.di

import com.example.weatherapplication.database.repository.ViewedCityRepository
import com.example.weatherapplication.database.repository.ViewedCityRepositoryImpl
import dagger.Binds
import dagger.Module

/**
 * @author Kalmykova Natalia
 */
@Module
abstract class ViewedCityRepositoryModule {
    @Binds
    abstract fun cityRepository(cityRepository: ViewedCityRepositoryImpl): ViewedCityRepository
}