package com.example.covidtracker.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.covidtracker.repository.CovidRepository

class MainViewModel(private val covidRepository: CovidRepository) : ViewModel() {

    private val _death = MutableLiveData<Int>()
    private val _deathIncrement = MutableLiveData<Int>()
    private val _positive = MutableLiveData<Int>()
    private val _positiveIncrement = MutableLiveData<Int>()
    private val _recovered = MutableLiveData<Int>()
    private val _date = MutableLiveData<Int>()

    val death: LiveData<Int>
        get() = _death
    val deathIncrement: LiveData<Int>
        get() = _deathIncrement
    val positive: LiveData<Int>
        get() = _positive
    val positiveIncrement: LiveData<Int>
        get() = _positiveIncrement
    val recovered: LiveData<Int>
        get() = _recovered
    val date: LiveData<Int>
        get() = _date


    fun searchCovid() {
        covidRepository.getCovidData(
            { covid ->
                _death.value = covid.death
                _deathIncrement.value = covid.deathIncrease
                _positive.value = covid.positive
                _positiveIncrement.value = covid.positiveIncrease
                _recovered.value = covid.recovered
                _date.value = covid.date},
            { t -> Log.e("MainActivity", "onFailure: ", t) }
        )
    }
}