package com.example.covidtracker.viewmodel

import com.example.covidtracker.DataApi
import com.example.covidtracker.repository.CovidRepository
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun providesMainViewModelFactory(covidRepository: CovidRepository): MainViewModelFactory {
        return MainViewModelFactory(covidRepository)
    }
}