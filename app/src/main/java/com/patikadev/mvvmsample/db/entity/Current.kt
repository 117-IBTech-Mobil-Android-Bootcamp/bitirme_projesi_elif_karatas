package com.patikadev.mvvmsample.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_WEATHER_ID = 0
@Entity(tableName = "CURRENT")
data class Current (
    @ColumnInfo (name="temp_c") val temp_c : Double,
    @ColumnInfo(name="temp_f") val temp_f : Double,
    @Embedded(prefix = "condition_")
    val condition: Condition,
    @ColumnInfo(name="feelslike_c") val feelslike_c : Double,
    @ColumnInfo(name="feelslike_f") val feelslike_f : Double
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID
}