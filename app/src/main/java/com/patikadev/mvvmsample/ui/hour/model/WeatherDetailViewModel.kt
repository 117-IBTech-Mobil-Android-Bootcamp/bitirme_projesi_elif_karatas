package com.patikadev.mvvmsample.ui.hour.model

import androidx.lifecycle.ViewModel
import com.patikadev.mvvmsample.network.repository.WeatherRepository
import com.patikadev.mvvmsample.util.UnitSystem
import com.patikadev.mvvmsample.util.lazyDeferred

class WeatherDetailViewModel(private val weatherRepository: WeatherRepository,
) : ViewModel(){

    private val unitSystem = UnitSystem.METRIC

    val isMetric: Boolean get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred {
        weatherRepository.getWeatherDetails(isMetric)
    }
    val weatherLocation by lazyDeferred {
        weatherRepository.getWeatherLocation()
    }
}