package com.oleksandr.demsky.weatherapp.network.model

data class CityWeather(
    var city: City,
    var cod: Int,
    var message: String,
    var cnt: Int,
    var list: List<Weathers>
)

data class City(
    var id: Int,
    var name: String,
    var coord: Coord,
    var country: String,
    var population: Int,
    var timezone: Int
)


data class Weathers(
    var dt: Long,
    var sunrise: Int,
    var sunset: Int,
    var temp: Temp,
    var pressure: Float,
    var humidity: Float,
    var weather: List<Weather>,
    var speed: Float,
    var deg: Float,
    var clouds: Float,
    var rain: Float
)

data class Temp(
    var day: Float,
    var min: Float,
    var max: Float,
    var night: Float,
    var eve: Float,
    var morn: Float
)
