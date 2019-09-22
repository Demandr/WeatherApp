package com.oleksandr.demsky.weatherapp.ui.model

data class City(
    var id: Int,
    var name: String,
    var img: String,
    var temp: Float,
    var description: String,
    var dt: Long,
    var humidity: Float? = null,
    var pressure: Float? = null
)