package com.patikadev.mvvmsample.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.patikadev.mvvmsample.db.entity.*
import com.patikadev.mvvmsample.db.unitlocalized.hour.ImperialWeatherDetailEntry
import com.patikadev.mvvmsample.db.unitlocalized.hour.MetricWeatherDetailEntry

@Dao
interface DetailsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(hour: List<Hour>)

    @Query("select * from hour")
    fun getSimpleWeatherForecastsMetric(): LiveData<List<MetricWeatherDetailEntry>>

    @Query("select * from hour")
    fun getSimpleWeatherForecastsImperial(): LiveData<List<ImperialWeatherDetailEntry>>
}