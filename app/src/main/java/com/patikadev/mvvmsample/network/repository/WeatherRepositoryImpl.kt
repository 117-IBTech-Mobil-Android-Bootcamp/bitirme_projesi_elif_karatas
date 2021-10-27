package com.patikadev.mvvmsample.network.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.patikadev.mvvmsample.db.DetailsDAO
import com.patikadev.mvvmsample.db.WeatherDAO
import com.patikadev.mvvmsample.db.WeatherLocationDAO
import com.patikadev.mvvmsample.db.entity.WeatherLocation
import com.patikadev.mvvmsample.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.patikadev.mvvmsample.db.unitlocalized.hour.UnitSpecificWeatherDetailEntry
import com.patikadev.mvvmsample.network.WeatherNetworkDataSource
import com.patikadev.mvvmsample.network.response.CurrentWeatherResponse
import com.patikadev.mvvmsample.network.response.WeatherDetailResponse
import com.patikadev.mvvmsample.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import org.threeten.bp.ZonedDateTime
import java.util.*

class WeatherRepositoryImpl(private val weatherNetworkDataSource: WeatherNetworkDataSource,
                            private val weatherDao: WeatherDAO,
                            private val locationProvider: LocationProvider,
                            private val weatherLocationDAO: WeatherLocationDAO,
                            private val detailsDAO: DetailsDAO)
    :WeatherRepository{
    init {
        weatherNetworkDataSource.apply {
            downloadedCurrentWeather.observeForever {
                    newCurrentWeather ->
                persistFetchedCurrentWeather(newCurrentWeather)
            }
            downloadedWeatherDetail.observeForever {
                    newWeatherDetail ->
                persistFetchedWeatherDetail(newWeatherDetail)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>{
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext if (metric) weatherDao.getCurrentWeatherMetric()
            else weatherDao.getCurrentWeatherImperial()
        }
    }

    override suspend fun getWeatherDetails(metric: Boolean): LiveData<out List<UnitSpecificWeatherDetailEntry>> {
        return withContext (Dispatchers.IO){
            initWeatherData()
            return@withContext if(metric) detailsDAO.getSimpleWeatherForecastsMetric()
            else detailsDAO.getSimpleWeatherForecastsImperial()
        }
    }

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO) {
            return@withContext weatherLocationDAO.getLocation()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse){
        GlobalScope.launch(Dispatchers.IO) {
            weatherDao.upsert(fetchedWeather.current)
            weatherLocationDAO.upsert(fetchedWeather.weatherLocation)
        }
    }

    private fun persistFetchedWeatherDetail(fetchedWeather: WeatherDetailResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            val weatherDetailList = fetchedWeather.forecastday.hour
            detailsDAO.insert(weatherDetailList)
            weatherLocationDAO.upsert(fetchedWeather.weatherLocation)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun initWeatherData(){
        val lastWeatherLocation = weatherLocationDAO.getLocationNonLive()

        if(lastWeatherLocation == null || locationProvider.hasLocationChanged(lastWeatherLocation)){
            fetchCurrentWeather()
            fetchWeatherDetail()
            return
        }

        if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather()

        if(isFetchWeatherDetailNeeded(lastWeatherLocation.zonedDateTime))
            fetchWeatherDetail()
    }

    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocationString()
        )
    }

    private suspend fun fetchWeatherDetail() {
        weatherNetworkDataSource.fetchWeatherDetail(
            locationProvider.getPreferredLocationString()
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean{
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

    private fun isFetchWeatherDetailNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val dayAgo = ZonedDateTime.now().minusHours(24)
        return lastFetchTime.isBefore(dayAgo)
    }
}