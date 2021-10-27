package com.patikadev.mvvmsample

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.patikadev.mvvmsample.db.WeatherDB
import com.patikadev.mvvmsample.network.*
import com.patikadev.mvvmsample.network.repository.WeatherRepository
import com.patikadev.mvvmsample.network.repository.WeatherRepositoryImpl
import com.patikadev.mvvmsample.provider.LocationProvider
import com.patikadev.mvvmsample.provider.LocationProviderImpl
import com.patikadev.mvvmsample.ui.current.model.WeatherViewModelFactory
import com.patikadev.mvvmsample.ui.hour.model.WeatherDetailViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherApplication : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@WeatherApplication))

        bind() from singleton { WeatherDB(instance())}

        bind() from singleton { instance<WeatherDB>().weatherDao()}
        bind() from singleton { instance<WeatherDB>().weatherDetailsDao()}
        bind() from singleton { instance<WeatherDB>().weatherLocationDao() }

        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance())}

        bind() from singleton { WeatherAPI(instance())}

        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance())}

        bind<WeatherRepository>() with singleton { WeatherRepositoryImpl(instance(),instance(),instance(),instance(),instance())}

        bind() from provider { WeatherViewModelFactory(instance()) }
        bind() from provider { WeatherDetailViewModelFactory(instance()) }

        bind<LocationProvider>() with singleton { LocationProviderImpl(instance())}
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        androidx.preference.PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }
}