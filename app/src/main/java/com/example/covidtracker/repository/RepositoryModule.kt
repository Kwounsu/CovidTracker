package com.example.covidtracker.repository

import com.example.covidtracker.DataApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesCovidRepository(api: DataApi): CovidRepository {
        return CovidRepositoryImpl(api)
    }
}