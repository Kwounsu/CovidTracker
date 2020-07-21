package com.example.covidtracker

import com.example.covidtracker.repository.RepositoryModule
import com.example.covidtracker.viewmodel.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class, RepositoryModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}