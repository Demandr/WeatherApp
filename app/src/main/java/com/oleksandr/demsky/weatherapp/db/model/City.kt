package com.oleksandr.demsky.weatherapp.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class City(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "city_name") val cityName: String
)