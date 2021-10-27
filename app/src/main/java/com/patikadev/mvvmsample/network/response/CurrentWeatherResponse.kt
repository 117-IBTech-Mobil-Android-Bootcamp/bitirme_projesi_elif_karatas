package com.patikadev.mvvmsample.network.response

import android.location.Location
import com.google.gson.annotations.SerializedName
import com.patikadev.mvvmsample.db.entity.Current
import com.patikadev.mvvmsample.db.entity.WeatherLocation

data class CurrentWeatherResponse (
    @SerializedName("location") val weatherLocation: WeatherLocation,
    @SerializedName("current") val current : Current
    )