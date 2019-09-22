package com.oleksandr.demsky.weatherapp.ui.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.oleksandr.demsky.weatherapp.Injection
import com.oleksandr.demsky.weatherapp.R
import com.oleksandr.demsky.weatherapp.network.WeatherEndpoint
import com.oleksandr.demsky.weatherapp.network.model.CurrentWeatherCity
import com.oleksandr.demsky.weatherapp.ui.adapter.WeatherPagerAdapter
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_weather_info.*


class WeatherInfoFragment : Fragment(R.layout.fragment_weather_info), Injection {
    lateinit var repository: WeatherEndpoint
    lateinit var cityName: String

    override fun injectRepository(repository: WeatherEndpoint) {
        this.repository = repository
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cityName = it.getString(NAME_CITY, "")
        }
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager.adapter = WeatherPagerAdapter(
            activity!!.supportFragmentManager,
            arrayListOf(getString(R.string.three_days), getString(R.string.seven_days))
        ).apply {
            addFragment(WeatherCityFewDaysFragment.newInstance(cityName, 3))
            addFragment(WeatherCityFewDaysFragment.newInstance(cityName))
        }
        tabLayout.setupWithViewPager(viewPager, true)

        repository.getWeatherByCity(
            cityName,
            lang = resources.configuration.locale.language
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    showResult(result)
                },
                { t -> showError(t) }
            )
    }

    fun showResult(result: CurrentWeatherCity) {
        tvCityName.text = result.name
        Picasso.get().load("https://openweathermap.org/img/wn/${result.weather[0].icon}@2x.png")
            .into(imageView)
        tvTemp.text = "${result.main.temp.toInt()} °C"
        tvTempMin.text = getString(R.string.min_temp, "${result.main.temp_min.toInt()} °C")
        tvTempMax.text = getString(R.string.max_temp, "${result.main.temp_max.toInt()} °C")
        tvHumidity.text = getString(R.string.humidity, "${result.main.humidity.toInt()} %")
        tvPressure.text = getString(R.string.pressure, "${result.main.pressure.toInt()} hPa")

        tvDesciption.text = result.weather[0].description


    }

    fun showError(t: Throwable) {
        Log.e("sss", "${t.printStackTrace()}")
    }


    companion object {
        @JvmStatic
        fun newInstance(cityName: String) =
            WeatherInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(NAME_CITY, cityName)
                }
            }
    }

}
