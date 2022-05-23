package com.example.weatherapplication

import android.app.Application
import com.example.weatherapplication.di.AppComponent
import com.example.weatherapplication.di.DaggerAppComponent

/**
 * @author Kalmykova Natalia
 */
class App : Application() {

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    private fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }
}