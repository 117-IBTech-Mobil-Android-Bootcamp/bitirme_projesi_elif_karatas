package com.patikadev.mvvmsample.ui.current.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.patikadev.mvvmsample.db.entity.Current
import com.patikadev.mvvmsample.network.repository.WeatherRepository
import com.patikadev.mvvmsample.network.response.CurrentWeatherResponse

class WeatherViewModelFactory(private val weatherRepository: WeatherRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel(weatherRepository) as T
    }
}
