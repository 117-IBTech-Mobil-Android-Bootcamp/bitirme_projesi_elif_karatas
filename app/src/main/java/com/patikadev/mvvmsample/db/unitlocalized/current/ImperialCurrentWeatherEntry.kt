package com.patikadev.mvvmsample.db.unitlocalized.current

import androidx.room.ColumnInfo

data class ImperialCurrentWeatherEntry ( @ColumnInfo(name = "temp_c")
override val temp_c: Double,
    @ColumnInfo(name = "temp_f")
    override val temp_f: Double,
    @ColumnInfo(name = "feelslike_c")
    override val feelsLike_c: Double,
    @ColumnInfo(name = "feelslike_f")
    override val feelsLike_f: Double,
    @ColumnInfo(name = "condition_text")
    override val condition_text: String,
    @ColumnInfo(name = "condition_icon")
    override val condition_icon: String
    ) : UnitSpecificCurrentWeatherEntry