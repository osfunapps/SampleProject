package com.osapps.capitalslearner.main.listfragment.view

import com.osapps.capitalslearner.di.MainView
import com.osapps.capitalslearner.main.listfragment.model.CountryObj
import com.osapps.capitalslearner.main.listfragment.model.InterceptionTypes

/**
 * Created by osApps on 02/06/2017.
 */

interface CountriesFragView: MainView {
    fun onListUpdated()
    fun markNarratedCountry(narratedCountryIdx: Int)
    fun setClickInterceptionType(type: InterceptionTypes)
    fun onNarrationDone()
    fun toggleClickInterception(visibility: Int)
    fun loadAdapter(countriesList: ArrayList<CountryObj>)
    fun popCardDialog()
    fun updateCardInput(interceptionType: InterceptionTypes, input: String)
}
