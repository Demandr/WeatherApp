package com.oleksandr.demsky.weatherapp

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.oleksandr.demsky.weatherapp.db.AppDatabase
import com.oleksandr.demsky.weatherapp.db.model.City
import com.oleksandr.demsky.weatherapp.network.WeatherEndpoint
import com.oleksandr.demsky.weatherapp.network.getHttpClient
import com.oleksandr.demsky.weatherapp.network.getRetrofit
import java.util.concurrent.Executors


class App : Application() {
    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()

        database = buildDb()
        database.openHelper.readableDatabase
        registerActivityLifecycleCallbacks(
            Injector(
                getRetrofit(getHttpClient()).create(WeatherEndpoint::class.java),
                database
            )
        )
    }

    private fun buildDb() = Room.databaseBuilder(
        this,
        AppDatabase::class.java,
        "database_city"
    ).addCallback(object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Executors.newSingleThreadScheduledExecutor()
                .execute {
                    database.userDao().insertCity(
                        City(703448, "Kiev"),
                        City(689558, "Vinnytsya")
                    )
                }
        }
    }).build()
}