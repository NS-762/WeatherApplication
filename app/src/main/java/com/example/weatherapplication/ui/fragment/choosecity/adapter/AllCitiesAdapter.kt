package com.example.weatherapplication.ui.fragment.choosecity.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.databinding.ItemCityBinding
import com.example.weatherapplication.model.CityModel
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Kalmykova Natalia
 */
class AllCitiesAdapter : RecyclerView.Adapter<AllCitiesAdapter.ChooseCityViewHolder>(),
    Filterable {

    var onItemClickListener: ((CityModel) -> Unit)? = null

    var items: List<CityModel> = listOf()
        set(value) {
            field = value
            filterItems = value
            notifyDataSetChanged()
        }

    var filterItems: List<CityModel> = listOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseCityViewHolder {
        val binding =
            ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChooseCityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChooseCityViewHolder, position: Int) {
        holder.bind(filterItems[position])
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                filterItems = if (charSearch.isEmpty()) {
                    items
                } else {
                    val resultList = ArrayList<CityModel>()
                    for (item in items) {
                        if (item.name.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(item)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterItems
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterItems = results?.values as List<CityModel>
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int = filterItems.size

    inner class ChooseCityViewHolder(private val binding: ItemCityBinding) :
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