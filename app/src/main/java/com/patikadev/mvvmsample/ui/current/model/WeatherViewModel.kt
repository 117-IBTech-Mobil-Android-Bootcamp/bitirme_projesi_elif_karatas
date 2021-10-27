package com.patikadev.mvvmsample.ui.current.model

import androidx.lifecycle.*
import com.patikadev.mvvmsample.network.repository.WeatherRepository
import com.patikadev.mvvmsample.network.repository.WeatherRepositoryImpl
import com.patikadev.mvvmsample.util.UnitSystem
import com.patikadev.mvvmsample.util.lazyDeferred

class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    private val unitSystem = UnitSystem.METRIC

    val isMetric: Boolean get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred {
        weatherRepository.getCurrentWeather(isMetric)
    }
    val weatherLocation by lazyDeferred {
        weatherRepository.getWeatherLocation()
    }
}