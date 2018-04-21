package com.osapps.capitalslearner.main.listfragment.presentation.narrator

/**
 * Created by osapps on 20/04/2018.
 */
interface CountriesNarratorCallback {
    fun onCountryNarrated(countryObjPos: Int)
    fun onCapitalNarrated(capitalObjPos: Int)
    fun onNarrationDone()
}

