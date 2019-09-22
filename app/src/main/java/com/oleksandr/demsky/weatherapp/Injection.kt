package com.oleksandr.demsky.weatherapp

import com.oleksandr.demsky.weatherapp.db.AppDatabase
import com.oleksandr.demsky.weatherapp.network.WeatherEndpoint

interface Injection {
    fun injectRepository(repository: WeatherEndpoint)
    fun injectDatabase(db: AppDatabase) {}
}