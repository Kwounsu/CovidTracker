package com.example.covidtracker.repository

import com.example.covidtracker.DataApi
import com.example.covidtracker.CovidData
import retrofit2.*

class CovidRepositoryImpl(private val api: DataApi) :
    CovidRepository {

    override fun getCovidData(onSuccess: (user: CovidData) -> Unit, onFailure: (t: Throwable) -> Unit) {
        api.getCovidData().enqueue(object : Callback<ArrayList<CovidData>> {
            override fun onResponse(call: Call<ArrayList<CovidData>>, response: Response<ArrayList<CovidData>>) {
                response.body()?.let { covid ->
                    onSuccess.invoke(covid[0])
                }
            }

            override fun onFailure(call: Call<ArrayList<CovidData>>, t: Throwable) {
                onFailure.invoke(t)
            }
        })
    }
}