package com.oleksandr.demsky.weatherapp.network

import androidx.annotation.NonNull
import com.oleksandr.demsky.weatherapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val API_KEY = "e023909b4f260a0fda95daa06bd93708"

fun getHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(requestInterceptor())
        .build()
}

private fun requestInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    if (BuildConfig.DEBUG)
        this.level = HttpLoggingInterceptor.Level.BODY
    else
        this.level = HttpLoggingInterceptor.Level.NONE
}

fun getRetrofit(@NonNull okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}