package com.patikadev.mvvmsample.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

const val LOCATION_ID = 0
@Entity(tableName = "LOCATION")
data class WeatherLocation (
    val name: String,
    val region: String,
    val country: String,
    val localtime_epoch: Long,
    @SerializedName("tz_id")
    val tz_id: String
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = LOCATION_ID

    val zonedDateTime: ZonedDateTime
        get() {
            val instant = Instant.ofEpochSecond(localtime_epoch)
            val zoneId = ZoneId.of(tz_id)
            return ZonedDateTime.ofInstant(instant,zoneId)
        }
}