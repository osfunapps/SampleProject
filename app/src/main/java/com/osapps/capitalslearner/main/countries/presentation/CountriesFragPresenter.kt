package com.osapps.capitalslearner.main.countries.presentation

import com.osapps.capitalslearner.di.MainPresenter
import com.osapps.capitalslearner.main.countries.model.CountryObj
import com.osapps.capitalslearner.main.countries.presentation.narrator.CountriesNarratorCallback

/**
 * Created by osApps on 02/06/2017.
 */

interface CountriesFragPresenter: MainPresenter, CountriesNarratorCallback {
    //list items tools
    fun countriesList(): ArrayList<CountryObj>
    fun onCountryAdded(countryObj: CountryObj)
    fun removeCountry(countryObj: CountryObj)

    //narration
    fun startRandomNarrationSequence()
    fun narrateNextCapital()
    fun narrateNextCountry()
    fun narratePreviousCountry()
    fun narratePreviousCapital()

}
