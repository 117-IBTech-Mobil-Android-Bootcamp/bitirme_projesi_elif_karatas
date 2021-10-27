package com.patikadev.mvvmsample.ui.current.fragment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.patikadev.mvvmsample.R
import com.patikadev.mvvmsample.base.BaseFragment
import com.patikadev.mvvmsample.databinding.FragmentHomeBinding
import com.patikadev.mvvmsample.db.unitlocalized.current.ImperialCurrentWeatherEntry
import com.patikadev.mvvmsample.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.patikadev.mvvmsample.ui.current.adapter.ViewPagerAdapter
import com.patikadev.mvvmsample.ui.current.model.WeatherViewModel
import com.patikadev.mvvmsample.ui.current.model.WeatherViewModelFactory
import com.patikadev.mvvmsample.util.*
import kotlinx.android.synthetic.main.card_item_current.*
import kotlinx.android.synthetic.main.card_item_current.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class HomeFragment : BaseFragment<FragmentHomeBinding>(), KodeinAware {
    private var viewPagerAdapter: ViewPagerAdapter? = null
    private var currentList = mutableListOf<ImperialCurrentWeatherEntry>()
    override val kodein by closestKodein()
    private val viewModelFactory: WeatherViewModelFactory by instance()

    private lateinit var viewModel: WeatherViewModel

    override fun getLayoutID() = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(WeatherViewModel::class.java)

        var cities = resources.getStringArray(R.array.cities)
        var adapter = ArrayAdapter(requireContext(),android.R.layout.simple_expandable_list_item_1,cities)
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.threshold=2
        bindUI()
    }

    override fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()
        val weatherLocation = viewModel.weatherLocation.await()

        weatherLocation.observe(viewLifecycleOwner, Observer { location ->
            if (location == null) return@Observer
            updateLocation(location.name)
        })
        currentWeather.observe(viewLifecycleOwner, Observer {
            if(it==null) return@Observer
            updateDateToToday()
            loadCards(it)
        })
    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
        addLocation.setOnClickListener {
        }
    }

    private fun updateDateToToday() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    fun loadCards(unitSpecificCurrentWeatherEntry: UnitSpecificCurrentWeatherEntry){
        currentList = ArrayList()
        addLocation.setOnClickListener {
            warningText.gone()
            currentList.add(
                ImperialCurrentWeatherEntry(
                    unitSpecificCurrentWeatherEntry.temp_c,
                    unitSpecificCurrentWeatherEntry.temp_f,
                    unitSpecificCurrentWeatherEntry.feelsLike_c,
                    unitSpecificCurrentWeatherEntry.feelsLike_f,
                    unitSpecificCurrentWeatherEntry.condition_text,
                    unitSpecificCurrentWeatherEntry.condition_icon,
                )
            )
            // setup adapter
            viewPagerAdapter = ViewPagerAdapter(requireContext(),currentList)

            // set adapter to viewpager
            viewPager.adapter = viewPagerAdapter

            // set default padding
            viewPager.setPadding(100, 0, 100, 0)
        }
    }
}
