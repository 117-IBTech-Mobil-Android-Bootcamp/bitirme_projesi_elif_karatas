package com.patikadev.mvvmsample.provider

import android.content.Context
import com.patikadev.mvvmsample.db.entity.WeatherLocation

const val CUSTOM_LOCATION = "CUSTOM_LOCATION"

class LocationProviderImpl(context: Context) : PreferenceProvider(context), LocationProvider{

    private val appContext = context.applicationContext

    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        return hasCustomLocationChanged(lastWeatherLocation)
    }

    override suspend fun hasCustomLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        val customLocationName = getCustomLocationName()
        return customLocationName != lastWeatherLocation.name
    }

    override suspend fun getPreferredLocationString(): String {
        return "${getCustomLocationName()}"
    }


    private fun getCustomLocationName(): String? {
        return preferences.getString(CUSTOM_LOCATION, null)
    }
}