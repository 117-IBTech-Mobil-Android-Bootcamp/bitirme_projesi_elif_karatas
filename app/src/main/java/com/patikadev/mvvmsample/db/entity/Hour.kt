package com.patikadev.mvvmsample.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "HOUR")
data class Hour(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "time")val time : String,
    @ColumnInfo(name = "temp_c")val temp_c : Double,
    @ColumnInfo(name = "temp_f")val temp_f : Double,
    @Embedded(prefix = "condition_")
    val condition: Condition,
    @ColumnInfo(name = "feelslike_c")
    val feelslike_c : Double,
    @ColumnInfo(name = "feelslike_f")
    val feelslike_f : Double
)