package com.patikadev.mvvmsample.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.patikadev.mvvmsample.network.response.CurrentWeatherResponse
import com.patikadev.mvvmsample.network.response.WeatherDetailResponse
import com.patikadev.mvvmsample.util.API_KEY
import com.patikadev.mvvmsample.util.BASE_URL
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface WeatherAPI {

    @GET("current.json")
    fun getCurrentWeather(@Query("q") q: String
    ): Deferred<CurrentWeatherResponse>

    @GET("forecast.json")
    fun getWeatherDetail(
        @Query("q") location: String,
        @Query("days") days: Int,
    ): Deferred<WeatherDetailResponse>

    companion object{
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): WeatherAPI {
            val requestInterceptor = Interceptor{ chain ->
                val url = chain.request()
                    .url
                    .newBuilder()
                    .addQueryParameter("key", API_KEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherAPI::class.java)
        }
    }
}