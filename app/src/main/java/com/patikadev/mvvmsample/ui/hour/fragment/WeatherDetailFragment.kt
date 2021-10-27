package com.patikadev.mvvmsample.ui.hour.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.patikadev.mvvmsample.R
import com.patikadev.mvvmsample.base.BaseFragment
import com.patikadev.mvvmsample.databinding.FragmentWeatherDetailBinding
import com.patikadev.mvvmsample.db.unitlocalized.hour.UnitSpecificWeatherDetailEntry
import com.patikadev.mvvmsample.ui.hour.adapter.HourItem
import com.patikadev.mvvmsample.ui.hour.adapter.RecyclerViewAdapter
import com.patikadev.mvvmsample.ui.hour.model.WeatherDetailViewModel
import com.patikadev.mvvmsample.ui.hour.model.WeatherDetailViewModelFactory
import com.patikadev.mvvmsample.util.gone
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_weather_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class WeatherDetailFragment : BaseFragment<FragmentWeatherDetailBinding>(), KodeinAware {
    override val kodein by closestKodein()
    private val weatherDetailViewModelFactory: WeatherDetailViewModelFactory by instance()
    private lateinit var viewModel: WeatherDetailViewModel

    private val hourList = mutableListOf<UnitSpecificWeatherDetailEntry>()
    private var adapter : RecyclerViewAdapter? = null

    override fun getLayoutID() = R.layout.fragment_weather_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, weatherDetailViewModelFactory)
            .get(WeatherDetailViewModel::class.java)

        bindUI()
    }

    override fun bindUI() = launch(Dispatchers.Main) {
        val weather = viewModel.weather.await()
        val weatherLocation = viewModel.weatherLocation.await()

        weatherLocation.observe(viewLifecycleOwner, Observer { location ->
            if (location == null) return@Observer
            updateLocation(location.name)
        })

        weather.observe(viewLifecycleOwner, Observer { weatherEntries ->
            if (weatherEntries == null) return@Observer
            textView.gone()
            progressBar.gone()
            updateWeatherByHour()
            recyclerView.adapter = adapter
            //initRecyclerView(weatherEntries.toHourItems())
        })
    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
        locationDetail.text = location
    }

    private fun updateWeatherByHour() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Weather by hour"
    }

    private fun List<UnitSpecificWeatherDetailEntry>.toHourItems() : List<HourItem> {
        return this.map {
            HourItem(it)
        }
    }

    private fun initRecyclerView(items: List<HourItem>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@WeatherDetailFragment.context)
            adapter = groupAdapter
        }

        /*groupAdapter.setOnItemClickListener { item, view ->
            (item as? HourItem)?.let {
                showWeatherDetail(it.weatherEntry.date, view)
            }
        }*/
    }
}