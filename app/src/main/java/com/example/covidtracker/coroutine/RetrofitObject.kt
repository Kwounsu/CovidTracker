package com.example.covidtracker.coroutine

import com.example.covidtracker.DataApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://covidtracking.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApiService(): DataApi {
        return getRetrofit().create(DataApi::class.java)
    }
}