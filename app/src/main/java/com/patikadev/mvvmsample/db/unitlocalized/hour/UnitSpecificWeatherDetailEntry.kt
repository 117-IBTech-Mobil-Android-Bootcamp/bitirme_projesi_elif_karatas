package com.patikadev.mvvmsample.db.unitlocalized.hour

interface UnitSpecificWeatherDetailEntry{
    val temp_c: Double
    val temp_f: Double
    val feelsLike_c: Double
    val feelsLike_f: Double
    val condition_text: String
    val condition_icon: String
    val time: String
}