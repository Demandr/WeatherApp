package com.oleksandr.demsky.weatherapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.oleksandr.demsky.weatherapp.Injection
import com.oleksandr.demsky.weatherapp.R
import com.oleksandr.demsky.weatherapp.network.WeatherEndpoint
import com.oleksandr.demsky.weatherapp.ui.fragment.CitiesWeatherFragment


class MainActivity : AppCompatActivity(R.layout.activity_main), Injection {

    lateinit var repository: WeatherEndpoint

    override fun injectRepository(repository: WeatherEndpoint) {
        this.repository = repository
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportFragmentManager.backStackEntryCount == 0)
            nextFragment(CitiesWeatherFragment())
    }


    fun nextFragment(fragment: Fragment) {
        val sfm = supportFragmentManager.beginTransaction()
        sfm.replace(R.id.container, fragment)
        sfm.addToBackStack(null)
        sfm.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0)
            finish()
    }
}
