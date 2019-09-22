package com.oleksandr.demsky.weatherapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oleksandr.demsky.weatherapp.db.model.City

@Database(entities = [City::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): CityDao
}