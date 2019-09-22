package com.oleksandr.demsky.weatherapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oleksandr.demsky.weatherapp.R
import com.oleksandr.demsky.weatherapp.ui.model.City
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_city.view.*


class CitiesWeatherAdapter(
    private var items: List<City> = ArrayList(),
    private var listener: CitiesWeatherAdapterListener? = null
) : RecyclerView.Adapter<CitiesWeatherAdapter.CitiesWeatherAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesWeatherAdapterViewHolder {
//                return CitiesWeatherAdapterViewHolder(
//            DataBindingUtil.inflate(
//                LayoutInflater.from(parent.context),
//                R.layout.item_city, parent, false
//            ) as com.oleksandr.demsky.weatherapp.databinding.ItemCityBinding


        return CitiesWeatherAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_city,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CitiesWeatherAdapterViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    fun updateList(items: List<City>) {
        this.items = items
        notifyDataSetChanged()
    }

    interface CitiesWeatherAdapterListener {
        fun onItemClick(item: City)
    }

    class CitiesWeatherAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: City, listener: CitiesWeatherAdapterListener? = null) {
//            binding.setVariable(BR.city, item)
//            binding.executePendingBindings();

            itemView.tvCity.text = item.name
            itemView.tvTemp.text = "${item.temp.toInt()} °C"
            itemView.tvDescription.text = item.description



            Picasso.get().load("https://openweathermap.org/img/wn/${item.img}@2x.png").into(itemView.ivWeather)

            itemView.setOnClickListener {
                listener?.onItemClick(item)
            }
        }
    }
}