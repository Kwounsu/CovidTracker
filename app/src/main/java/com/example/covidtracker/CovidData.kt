package com.example.covidtracker

import com.google.gson.annotations.SerializedName

data class CovidData (
    @SerializedName("date") val date : Int,
    @SerializedName("states") val states : Int,
    @SerializedName("positive") val positive : Int,
    @SerializedName("negative") val negative : Int,
    @SerializedName("pending") val pending : Int,
    @SerializedName("hospitalizedCurrently") val hospitalizedCurrently : Int,
    @SerializedName("hospitalizedCumulative") val hospitalizedCumulative : Int,
    @SerializedName("inIcuCurrently") val inIcuCurrently : Int,
    @SerializedName("inIcuCumulative") val inIcuCumulative : Int,
    @SerializedName("onVentilatorCurrently") val onVentilatorCurrently : Int,
    @SerializedName("onVentilatorCumulative") val onVentilatorCumulative : Int,
    @SerializedName("recovered") val recovered : Int,
    @SerializedName("dateChecked") val dateChecked : String,
    @SerializedName("death") val death : Int,
    @SerializedName("hospitalized") val hospitalized : Int,
    @SerializedName("lastModified") val lastModified : String,
    @SerializedName("total") val total : Int,
    @SerializedName("totalTestResults") val totalTestResults : Int,
    @SerializedName("posNeg") val posNeg : Int,
    @SerializedName("deathIncrease") val deathIncrease : Int,
    @SerializedName("hospitalizedIncrease") val hospitalizedIncrease : Int,
    @SerializedName("negativeIncrease") val negativeIncrease : Int,
    @SerializedName("positiveIncrease") val positiveIncrease : Int,
    @SerializedName("totalTestResultsIncrease") val totalTestResultsIncrease : Int,
    @SerializedName("hash") val hash : String
)