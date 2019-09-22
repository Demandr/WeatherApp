package com.oleksandr.demsky.weatherapp.network

import com.oleksandr.demsky.weatherapp.network.model.CityWeather
import com.oleksandr.demsky.weatherapp.network.model.CurrentWeatherCity
import com.oleksandr.demsky.weatherapp.network.model.WeatherGroupCity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherEndpoint {
    @GET("weather")
    fun getWeatherByCity(
        @Query("q") cityName: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "en",
        @Query("appid") appid: String = API_KEY
    ): Single<CurrentWeatherCity>

    @GET("forecast/daily")
    fun getWeatherFewDays(
        @Query("q") cityName: String,
        @Query("cnt") cnt: String = "7",
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "en",
        @Query("appid") appid: String = API_KEY
    ): Single<CityWeather>

    @GET("forecast")
    fun getWeatherForecast(
        @Query("q") cityName: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "en",
        @Query("appid") appid: String = API_KEY
    ): Single<CityWeather>

    @GET("group")
    fun getWeatherCities(
        @Query("id") id: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "en",
        @Query("appid") appid: String = API_KEY
    ): Single<WeatherGroupCity>
}