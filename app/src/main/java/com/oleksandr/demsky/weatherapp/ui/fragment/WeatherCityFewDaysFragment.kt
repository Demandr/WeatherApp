package com.oleksandr.demsky.weatherapp.ui.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.oleksandr.demsky.weatherapp.Injection
import com.oleksandr.demsky.weatherapp.R
import com.oleksandr.demsky.weatherapp.network.WeatherEndpoint
import com.oleksandr.demsky.weatherapp.ui.adapter.FewDaysWeatherAdapter
import com.oleksandr.demsky.weatherapp.ui.model.City
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_weather_city_week.*


const val NAME_CITY = "name city"
const val COUNT_DAYS = "count days"

class WeatherCityFewDaysFragment : Fragment(R.layout.fragment_weather_city_week), Injection {

    private lateinit var repository: WeatherEndpoint
    private lateinit var cityName: String
    private var countDays: Int = 7

    override fun injectRepository(repository: WeatherEndpoint) {
        this.repository = repository
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cityName = it.getString(NAME_CITY, "")
            countDays = it.getInt(COUNT_DAYS, 7)
        }
    }


    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvWeathers?.layoutManager = LinearLayoutManager(context)
        rvWeathers?.adapter = FewDaysWeatherAdapter(cityName, countDays)

        repository.getWeatherFewDays(
            cityName,
            cnt = countDays.toString(),
            lang = resources.configuration.locale.language
        ).subscribeOn(Schedulers.io())
            .map { weather ->
                weather.list.map { item ->
                    City(
                        weather.city.id,
                        weather.city.name,
                        item.weather[0].icon,
                        item.temp.day,
                        item.weather[0].description,
                        item.dt,
                        item.humidity,
                        item.pressure
                    )
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> showResult(result) },
                { t -> showError(t) }
            )

    }

    fun showResult(result: List<City>) {
        (rvWeathers?.adapter as FewDaysWeatherAdapter).updateList(result)
    }

    fun showError(t: Throwable) {
        Log.e("ERROR", "${t.printStackTrace()}")
    }

    companion object {
        @JvmStatic
        fun newInstance(cityName: String, countDays: Int = 7) =
            WeatherCityFewDaysFragment().apply {
                arguments = Bundle().apply {
                    putString(NAME_CITY, cityName)
                    putInt(COUNT_DAYS, countDays)
                }
            }
    }

}
