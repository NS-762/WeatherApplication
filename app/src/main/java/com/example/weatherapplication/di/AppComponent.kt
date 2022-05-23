package com.example.weatherapplication.di

import android.content.Context
import com.example.weatherapplication.ui.fragment.choosecity.di.ChooseCityComponent
import com.example.weatherapplication.ui.fragment.settings.di.SettingsComponent
import com.example.weatherapplication.ui.fragment.weather.di.WeatherComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

/**
 * @author Kalmykova Natalia
 */
@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelBuilderModule::class,
        SubcomponentsModule::class,
        DatabaseModule::class,
        ViewedCityRepositoryModule::class
    ]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun context(): Context

    fun weatherComponent(): WeatherComponent.Factory
    fun settingsComponent(): SettingsComponent.Factory
    fun chooseCityComponent(): ChooseCityComponent.Factory
}

@Module(
    subcomponents = []
)
object SubcomponentsModule