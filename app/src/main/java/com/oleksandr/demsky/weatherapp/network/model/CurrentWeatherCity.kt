package com.oleksandr.demsky.weatherapp.network.model

import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherCity(
    var coord: Coord,
    var weather: List<Weather>,
    var base: String? = null,
    var main: Main,
    var visibility: Int? = null,
    var wind: Wind,
    var clouds: Clouds,
    var dt: Long,
    var sys: Sys,
    var timezone: Int,
    var id: Int,
    var name: String,
    var code: Int
)

@Serializable
data class Coord(
    var lon: Float,
    var lat: Float
)

@Serializable
data class Weather(
    var id: Int,
    var main: String,
    var description: String,
    var icon: String
)

@Serializable
data class Main(
    var temp: Float,
    var pressure: Float,
    var humidity: Float,
    var temp_min: Float,
    var temp_max: Float
)

@Serializable
data class Wind(
    var speed: Float,
    var deg: Float
)

@Serializable
data class Clouds(
    var all: Float
)

@Serializable
data class Sys(
    var type: Int,
    var id: Int,
    var message: Float,
    var country: String,
    var sunrise: Float,
    var sunset: Float
)

