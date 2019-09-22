package com.oleksandr.demsky.weatherapp.ui.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oleksandr.demsky.weatherapp.R
import com.oleksandr.demsky.weatherapp.ui.model.City
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_city.view.*
import kotlinx.android.synthetic.main.item_weather.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FewDaysWeatherAdapter(
    private val cityName: String = "",
    private val countDays: Int = 7,
    private var items: List<City> = ArrayList(),
    private var listener: WeekWeatherAdapterListener? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            viewType,
            parent,
            false
        )
        return when (countDays) {
            7 -> WeekWeatherAdapterViewHolder(v)
            else -> FewDaysWeatherAdapterViewHolder(v)
        }

    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WeekWeatherAdapterViewHolder -> holder.bind(cityName, items[position], listener)
            is FewDaysWeatherAdapterViewHolder -> holder.bind(cityName, items[position], listener)
        }

    }

    override fun getItemViewType(position: Int): Int =
        when (countDays) {
            7 -> R.layout.item_city
            else -> R.layout.item_weather
        }


    fun updateList(items: List<City>) {
        this.items = items
        notifyDataSetChanged()
    }

    interface WeekWeatherAdapterListener {
        fun onItemClick(item: City)
    }

    class WeekWeatherAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cityName: String = "", item: City, listener: WeekWeatherAdapterListener? = null) {
            itemView.tvCity.text = cityName
            itemView.tvTemp.text = "${item.temp.toInt()} °C"

            itemView.tvDescription.text = convertLongToTime(item.dt)
            Picasso.get().load("https://openweathermap.org/img/wn/${item.img}@2x.png")
                .into(itemView.ivWeather)

            itemView.setOnClickListener {
                listener?.onItemClick(item)
            }
        }
    }

    class FewDaysWeatherAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cityName: String = "", item: City, listener: WeekWeatherAdapterListener? = null) {
            itemView.tvWeatherCity.text = cityName
            itemView.tvWeatherTemp.text = "${item.temp.toInt()} °C"
            itemView.tvWeatherDescription.text = item.description
            itemView.tvHumidity.text = itemView.context.getString(R.string.humidity, "${item.humidity?.toInt()} %")
            itemView.tvPressure.text = itemView.context.getString(R.string.pressure, "${item.pressure?.toInt()} hPa")

            itemView.tvWeatherDate.text = convertLongToTime(item.dt)
            Picasso.get().load("https://openweathermap.org/img/wn/${item.img}@2x.png")
                .into(itemView.ivItemWeather)

            itemView.setOnClickListener {
                listener?.onItemClick(item)
            }
        }


    }


}

private fun convertLongToTime(time: Long): String {
    val format = SimpleDateFormat("dd/MM/yyyy")
    return format.format(Date(time * 1000))
}