package com.example.weatherapplication.ui.fragment.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.databinding.ItemWeatherBinding
import com.example.weatherapplication.model.WeatherForDayModel

/**
 * @author Kalmykova Natalia
 */
class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    var onItemClicked: ((item: WeatherForDayModel) -> Unit)? = null

    var items: List<WeatherForDayModel> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return WeatherViewHolder(ItemWeatherBinding.inflate(inflater, parent, false))
    }


    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class WeatherViewHolder(private val binding: ItemWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClicked?.invoke(items[absoluteAdapterPosition])
            }
        }

        fun bind(item: WeatherForDayModel) {
            binding.apply {
                dayOfWeek.text = item.date
                weatherPicture.setImageResource(item.weatherPicture)
                temperature.text = item.tempMax
            }
        }
    }
}