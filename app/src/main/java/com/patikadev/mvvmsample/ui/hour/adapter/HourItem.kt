package com.patikadev.mvvmsample.ui.hour.adapter

import com.patikadev.mvvmsample.R
import com.patikadev.mvvmsample.db.unitlocalized.hour.UnitSpecificWeatherDetailEntry
import com.patikadev.mvvmsample.util.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_weather_detail.*
import kotlinx.android.synthetic.main.row_hour_weather.*

class HourItem(
    private val weatherEntry: UnitSpecificWeatherDetailEntry
) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            date.text = weatherEntry.time
            updateTemperature()
            updateConditionImage()
        }
    }

    override fun getLayout() = R.layout.row_hour_weather

    private fun ViewHolder.updateTemperature() {
        celcius.text = weatherEntry.temp_c.toString()
        fahrenheit.text = weatherEntry.temp_c.toString()
        feelslike_celcius.text = weatherEntry.feelsLike_c.toString()
        feelslike_fahrenheit.text = weatherEntry.temp_f.toString()
        weather.text = weatherEntry.condition_text
    }

    private fun ViewHolder.updateConditionImage() {
        GlideApp.with(this.containerView)
            .load("http:" + weatherEntry.condition_icon)
            .into(imageView)
    }
}