package com.patikadev.mvvmsample.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.patikadev.mvvmsample.db.entity.LOCATION_ID
import com.patikadev.mvvmsample.db.entity.WeatherLocation

@Dao
interface WeatherLocationDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherLocation: WeatherLocation)

    @Query("select * from location where id = $LOCATION_ID")
    fun getLocation(): LiveData<WeatherLocation>

    @Query("select * from location where id = $LOCATION_ID")
    fun getLocationNonLive(): WeatherLocation?
}