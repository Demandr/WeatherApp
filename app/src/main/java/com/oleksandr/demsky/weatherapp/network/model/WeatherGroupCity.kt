package com.oleksandr.demsky.weatherapp.network.model

data class WeatherGroupCity(
    var cnt: Int,
    var list: List<CurrentWeatherCity>
)