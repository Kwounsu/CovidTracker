package com.example.covidtracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.covidtracker.repository.CovidRepository
import javax.inject.Inject

/**
 * ViewModelProvider.Factory: pass objects in a ViewModel’s constructor.
 */
@Suppress("UNCHECKED_CAST")
class MainViewModelFactory @Inject constructor (private val covidRepository: CovidRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(covidRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}