package com.patikadev.mvvmsample.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.patikadev.mvvmsample.db.unitlocalized.current.MetricCurrentWeatherEntry
import com.patikadev.mvvmsample.db.entity.CURRENT_WEATHER_ID
import com.patikadev.mvvmsample.db.entity.Current
import com.patikadev.mvvmsample.db.unitlocalized.current.ImperialCurrentWeatherEntry

@Dao
interface WeatherDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(current: Current)

    @Query("select * from current where id = $CURRENT_WEATHER_ID")
    fun getCurrentWeatherMetric(): LiveData<MetricCurrentWeatherEntry>

    @Query("select * from current where id = $CURRENT_WEATHER_ID")
    fun getCurrentWeatherImperial(): LiveData<ImperialCurrentWeatherEntry>
}