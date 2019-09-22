package com.oleksandr.demsky.weatherapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oleksandr.demsky.weatherapp.db.model.City
import io.reactivex.Flowable

@Dao
interface CityDao {
    @Query("SELECT * FROM city")
    fun getAllCity(): Flowable<List<City>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(vararg city: City)
}