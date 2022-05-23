package com.example.weatherapplication.ui.fragment.settings.di

import androidx.lifecycle.ViewModel
import com.example.weatherapplication.di.ViewModelKey
import com.example.weatherapplication.ui.base.BaseViewModel
import com.example.weatherapplication.ui.fragment.settings.SettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author Kalmykova Natalia
 */
@Module
abstract class SettingsModule {
    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindViewModel(viewModel: SettingsViewModel): ViewModel
}