package com.example.covidtracker

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://covidtracking.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit): DataApi {
        return retrofit.create(DataApi::class.java)
    }
}