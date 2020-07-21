package com.example.covidtracker

import retrofit2.Call
import retrofit2.http.GET

interface DataApi {
    @GET("api/v1/us/current.json")
    fun getCovidData(): Call<ArrayList<CovidData>>
}