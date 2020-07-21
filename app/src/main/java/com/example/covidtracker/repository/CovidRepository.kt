package com.example.covidtracker.repository

import com.example.covidtracker.CovidData

interface CovidRepository {

    fun getCovidData(onSuccess: (covidData: CovidData) -> Unit, onFailure: (t: Throwable) -> Unit)
}