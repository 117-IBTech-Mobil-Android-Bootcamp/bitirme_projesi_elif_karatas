package com.patikadev.mvvmsample.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.patikadev.mvvmsample.internal.NoConnectivityException
import com.patikadev.mvvmsample.network.response.CurrentWeatherResponse
import com.patikadev.mvvmsample.network.response.WeatherDetailResponse
import com.patikadev.mvvmsample.util.FORECAST_DAYS_COUNT

class WeatherNetworkDataSourceImpl(
    private val apiService: WeatherAPI
) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String) {
        try {
            val fetchedCurrentWeather = apiService
                .getCurrentWeather(location)
                .await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }

    private val _downloadedWeatherDetail = MutableLiveData<WeatherDetailResponse>()
    override val downloadedWeatherDetail: LiveData<WeatherDetailResponse>
        get() = _downloadedWeatherDetail
    override suspend fun fetchWeatherDetail(location: String) {
        try {
            val fetchedWeatherDetail = apiService
                .getWeatherDetail(location, FORECAST_DAYS_COUNT)
                .await()
            _downloadedWeatherDetail.postValue(fetchedWeatherDetail)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }
}