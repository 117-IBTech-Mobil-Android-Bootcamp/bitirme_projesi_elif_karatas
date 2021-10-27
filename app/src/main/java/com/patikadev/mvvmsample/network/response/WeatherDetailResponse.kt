package com.patikadev.mvvmsample.network.response

import com.google.gson.annotations.SerializedName
import com.patikadev.mvvmsample.db.entity.Forecastday
import com.patikadev.mvvmsample.db.entity.WeatherLocation

data class WeatherDetailResponse (
    @SerializedName("location") val weatherLocation: WeatherLocation,
    @SerializedName("forecast") val forecastday : Forecastday
    )