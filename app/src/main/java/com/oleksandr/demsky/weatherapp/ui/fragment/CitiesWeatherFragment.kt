package com.oleksandr.demsky.weatherapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.oleksandr.demsky.weatherapp.Injection
import com.oleksandr.demsky.weatherapp.R
import com.oleksandr.demsky.weatherapp.db.AppDatabase
import com.oleksandr.demsky.weatherapp.network.WeatherEndpoint
import com.oleksandr.demsky.weatherapp.ui.MainActivity
import com.oleksandr.demsky.weatherapp.ui.adapter.CitiesWeatherAdapter
import com.oleksandr.demsky.weatherapp.ui.model.City
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_cities_weather.view.*


class CitiesWeatherFragment : Fragment(), Injection {
    private lateinit var binding: ViewDataBinding
    private lateinit var repository: WeatherEndpoint
    private lateinit var db: AppDatabase

    override fun injectRepository(repository: WeatherEndpoint) {
        this.repository = repository
    }

    override fun injectDatabase(db: AppDatabase) {
        this.db = db
    }


    private val rvItemOnClick = object : CitiesWeatherAdapter.CitiesWeatherAdapterListener {
        override fun onItemClick(item: City) {
            (activity as? MainActivity)?.nextFragment(
                WeatherInfoFragment.newInstance(
                    item.name
                )
            )
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_cities_weather, container, false)

        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.fab.setOnClickListener {
            (activity as? MainActivity)?.nextFragment(AddCityFragment())
        }

        binding.root.rvCities.layoutManager = LinearLayoutManager(context)
        binding.root.rvCities.adapter = CitiesWeatherAdapter(
            ArrayList(),
            rvItemOnClick
        )
        val lang = resources.configuration.locale.language
        db.userDao().getAllCity()
            .observeOn(AndroidSchedulers.mainThread())
            .map { list -> list.map { it.id } }
            .subscribe {
                repository.getWeatherCities(
                    it.joinToString(separator = ","),
                    lang = lang
                ).subscribeOn(Schedulers.io())
                    .map { list ->
                        list.list.map { city ->
                            City(
                                city.id,
                                city.name,
                                city.weather[0].icon,
                                city.main.temp,
                                city.weather[0].description,
                                city.dt
                            )
                        }
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result -> showResult(result) },
                        { t -> showError(t) }
                    )
            }


    }

    fun showResult(result: List<City>) {
        (binding.root.rvCities.adapter as CitiesWeatherAdapter).updateList(result)
    }

    fun showError(t: Throwable) {
        Log.e("sss", "${t.printStackTrace()}")
    }
}
