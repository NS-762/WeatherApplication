package com.example.weatherapplication.ui.fragment.choosecity.di

import com.example.weatherapplication.ui.fragment.choosecity.ChooseCityFragment
import dagger.Subcomponent

/**
 * @author Kalmykova Natalia
 */
@Subcomponent(modules = [ChooseCityModule::class])
interface ChooseCityComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ChooseCityComponent
    }

    fun inject(fragment: ChooseCityFragment)
}