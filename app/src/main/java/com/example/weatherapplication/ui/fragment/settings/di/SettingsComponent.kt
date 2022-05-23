package com.example.weatherapplication.ui.fragment.settings.di

import com.example.weatherapplication.ui.fragment.settings.SettingsFragment
import dagger.Subcomponent

/**
 * @author Kalmykova Natalia
 */
@Subcomponent(modules = [SettingsModule::class])
interface SettingsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): SettingsComponent
    }

    fun inject(fragment: SettingsFragment)
}