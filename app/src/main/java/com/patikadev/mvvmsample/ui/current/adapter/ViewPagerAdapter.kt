package com.patikadev.mvvmsample.ui.current.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.viewpager.widget.PagerAdapter
import com.patikadev.mvvmsample.R
import com.patikadev.mvvmsample.db.unitlocalized.current.ImperialCurrentWeatherEntry
import com.patikadev.mvvmsample.util.GlideApp
import kotlinx.android.synthetic.main.card_item_current.view.*

class ViewPagerAdapter(private val context: Context,private val viewPagerArrayList: List<ImperialCurrentWeatherEntry>): PagerAdapter(){
    override fun getCount(): Int {
        return viewPagerArrayList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        // inflate layout
        val view = LayoutInflater.from(context).inflate(R.layout.card_item_current, container, false)
        val model = viewPagerArrayList[position]
        val temp_c = model.temp_c
        val temp_f = model.temp_f
        val text = model.condition_text
        val feelslike_c = model.feelsLike_c
        val feelslike_f = model.feelsLike_f

        view.celcius.text = "Celcius: " + temp_c.toString() + " °C"
        view.fahrenheit.text = "Fahrenheit: " + temp_f.toString() + " °F"
        view.feelslike_celcius.text = "Feels like Celcius: " + feelslike_c.toString() + " °C"
        view.feelslike_fahrenheit.text = "Feels like Fahrenheit: " + feelslike_f.toString() + " °F"

        GlideApp.with(view)
            .load("http:${model.condition_icon}")
            .into(view.imageView)

        view.weather.text = text

        container.addView(view,position)

        // handle item
        view.setOnClickListener {
            findNavController(container).navigate(
                R.id.action_homeFragment_to_weatherDetailFragment
            )
        }
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object`as View)
    }
}