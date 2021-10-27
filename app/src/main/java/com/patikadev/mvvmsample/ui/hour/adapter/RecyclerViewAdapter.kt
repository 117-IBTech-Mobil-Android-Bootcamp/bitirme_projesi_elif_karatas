package com.patikadev.mvvmsample.ui.hour.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.patikadev.mvvmsample.R
import com.patikadev.mvvmsample.databinding.RowHourWeatherBinding
import com.patikadev.mvvmsample.db.unitlocalized.hour.UnitSpecificWeatherDetailEntry

class RecyclerViewAdapter(
    private val hourList: List<UnitSpecificWeatherDetailEntry>
) : RecyclerView.Adapter<RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.row_hour_weather,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val hour = this.hourList[position]
        holder.populate(hour)
    }

    override fun getItemCount() = this.hourList.size
}

class RecyclerViewHolder(private val binding : RowHourWeatherBinding) : RecyclerView.ViewHolder(binding.root) {

    fun populate(hour: UnitSpecificWeatherDetailEntry) {
        binding.bind = hour
        binding.executePendingBindings()
    }
}