package com.oleksandr.demsky.weatherapp.ui.fragment


import android.annotation.SuppressLint
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.oleksandr.demsky.weatherapp.Injection
import com.oleksandr.demsky.weatherapp.R
import com.oleksandr.demsky.weatherapp.db.AppDatabase
import com.oleksandr.demsky.weatherapp.db.model.City
import com.oleksandr.demsky.weatherapp.network.WeatherEndpoint
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_add_city.*


class AddCityFragment : Fragment(R.layout.fragment_add_city), Injection {

    private lateinit var repository: WeatherEndpoint
    private lateinit var db: AppDatabase

    override fun injectRepository(repository: WeatherEndpoint) {
        this.repository = repository
    }

    override fun injectDatabase(db: AppDatabase) {
        this.db = db
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etCityName?.requestFocus()
        showKeyboard()
        etCityName?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (etCityName?.text.toString().isNotBlank()) {
                    findCity(etCityName?.text.toString())
                    return@setOnEditorActionListener true
                }
            }
            false
        }
    }

    override fun onDetach() {
        closeKeyboard()
        super.onDetach()
    }

    @SuppressLint("CheckResult")
    private fun findCity(cityName: String) {
        repository.getWeatherByCity(
            cityName,
            lang = resources.configuration.locale.language
        ).subscribeOn(Schedulers.io())
            .subscribe(
                { result ->
                    Completable.fromAction {
                        db.userDao()
                            .insertCity(City(result.id, result.name))
                    }
                        .subscribeOn(Schedulers.io())
                        .subscribe { activity?.supportFragmentManager?.popBackStack() }
                },
                { t -> showError(t) }
            )
    }

    fun showError(t: Throwable) {
        etCityName.post { etCityName.error = getString(R.string.city_not_found) }
        Log.e("sss", "${t.printStackTrace()}")
    }

    private fun showKeyboard() {
        val inputMethodManager = context?.getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun closeKeyboard() {
        val inputMethodManager = context?.getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }
}
