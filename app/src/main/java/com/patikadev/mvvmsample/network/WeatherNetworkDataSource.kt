package com.patikadev.mvvmsample.network

import androidx.lifecycle.LiveData
import com.patikadev.mvvmsample.network.response.CurrentWeatherResponse
import com.patikadev.mvvmsample.network.response.WeatherDetailResponse


interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
    val downloadedWeatherDetail: LiveData<WeatherDetailResponse>

    suspend fun fetchCurrentWeather(
        location: String
    )
    suspend fun fetchWeatherDetail(
        location: String
    )
}