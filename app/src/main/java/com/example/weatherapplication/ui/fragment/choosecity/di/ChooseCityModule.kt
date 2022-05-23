package com.example.weatherapplication.ui.fragment.choosecity.di

import androidx.lifecycle.ViewModel
import com.example.weatherapplication.di.ViewModelKey
import com.example.weatherapplication.ui.fragment.choosecity.ChooseCityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author Kalmykova Natalia
 */
@Module
abstract class ChooseCityModule {
    @Binds
    @IntoMap
    @ViewModelKey(ChooseCityViewModel::class)
    abstract fun bindViewModel(viewModel: ChooseCityViewModel): ViewModel
}