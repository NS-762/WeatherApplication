package com.example.weatherapplication.ui.fragment.choosecity.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.databinding.ItemCityBinding
import com.example.weatherapplication.model.CityModel

/**
 * @author Kalmykova Natalia
 */
class ViewedCitiesAdapter : RecyclerView.Adapter<ViewedCitiesAdapter.ViewedCityViewHolder>() {

    var onItemClickListener: ((CityModel) -> Unit)? = null

    var items: List<CityModel> = listOf()
        set(value) {
            field = value
            filterItems = value
            notifyDataSetChanged()
        }

    var filterItems: List<CityModel> = listOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewedCityViewHolder {
        val binding =
            ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewedCityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewedCityViewHolder, position: Int) {
        holder.bind(filterItems[position])
    }

    override fun getItemCount(): Int = filterItems.size

    inner class ViewedCityViewHolder(private val binding: ItemCityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(filterItems[absoluteAdapterPosition])
            }
        }

        fun bind(item: CityModel) {
            binding.name.text = item.name
        }
    }
}