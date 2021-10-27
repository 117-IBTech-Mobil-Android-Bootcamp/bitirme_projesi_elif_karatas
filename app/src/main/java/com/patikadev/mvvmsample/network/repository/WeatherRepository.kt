package com.patikadev.mvvmsample.network.repository

import androidx.lifecycle.LiveData
import com.patikadev.mvvmsample.db.entity.WeatherLocation
import com.patikadev.mvvmsample.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.patikadev.mvvmsample.db.unitlocalized.hour.UnitSpecificWeatherDetailEntry

interface WeatherRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
    suspend fun getWeatherDetails(metric: Boolean): LiveData<out List<UnitSpecificWeatherDetailEntry>>
}