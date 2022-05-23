package com.example.weatherapplication.di

import android.content.Context
import com.example.weatherapplication.BuildConfig
import com.example.weatherapplication.data.api.Api
import com.example.weatherapplication.data.api.ApiWorkerImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author Kalmykova Natalia
 */
@Module
class AppModule {



    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiWorker(retrofit: Retrofit, context: Context): ApiWorkerImpl {
        val appApi = retrofit.create(Api::class.java)
        return ApiWorkerImpl(appApi, context)
    }
}