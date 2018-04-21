package com.osapps.capitalslearner.main.listfragment.presentation.list

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.osapps.capitalslearner.R
import com.osapps.capitalslearner.main.listfragment.model.CountryObj
import kotlinx.android.synthetic.main.country_list_item.view.*

/**
 * Created by osapps on 19/04/2018.
 */

class CountriesAdapter(private val longClickListener: (CountryObj) -> Boolean) : RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {

    private lateinit var countriesList: ArrayList<CountryObj>

    //because of dagger, we will need to insert the items AFTER adapter initialization, cause the adapter will be ready before them.
    fun setItems(countriesList: ArrayList<CountryObj>){
        this.countriesList = countriesList
        notifyDataSetChanged()
    }

    class ViewHolder(countryItem: ConstraintLayout) : RecyclerView.ViewHolder(countryItem) {

        val countryTV = countryItem.country_name_et!!
        val capitalTV = countryItem.capital_name_et!!
        val listItemPapa = countryItem.list_item_papa!!
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesAdapter.ViewHolder {
        val countryItem = LayoutInflater.from(parent.context).inflate(R.layout.country_list_item, parent, false) as ConstraintLayout
        return ViewHolder(countryItem)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.countryTV.text = countriesList.get(position).country
        holder.capitalTV.text = countriesList.get(position).capital
        holder.itemView.setOnLongClickListener{longClickListener(countriesList[position])}
    }

    override fun getItemCount(): Int {return countriesList.size}
}

