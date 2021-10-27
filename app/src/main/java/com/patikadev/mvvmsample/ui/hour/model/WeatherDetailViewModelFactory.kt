package com.patikadev.mvvmsample.ui.hour.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.patikadev.mvvmsample.network.repository.WeatherRepository
import com.patikadev.mvvmsample.ui.current.model.WeatherViewModel

class WeatherDetailViewModelFactory(private val weatherRepository: WeatherRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherDetailViewModel(weatherRepository) as T
    }
}