package com.patikadev.mvvmsample.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.patikadev.mvvmsample.db.entity.Current
import com.patikadev.mvvmsample.db.entity.Hour
import com.patikadev.mvvmsample.db.entity.WeatherLocation

@Database(entities = [Current::class,WeatherLocation::class,Hour::class],
version = 1)
abstract class WeatherDB : RoomDatabase() {
    abstract fun weatherDao() : WeatherDAO
    abstract fun weatherLocationDao(): WeatherLocationDAO
    abstract fun weatherDetailsDao(): DetailsDAO

    companion object{
        @Volatile private var instance: WeatherDB ?= null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                WeatherDB::class.java, "weather.db")
                .build()
    }
}