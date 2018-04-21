package com.osapps.capitalslearner.main.listfragment.presentation

import com.osapps.capitalslearner.infrastructure.LocalRepository
import com.osapps.capitalslearner.infrastructure.LocalRepositoryBank
import com.osapps.capitalslearner.main.listfragment.model.CountryObj
import com.osapps.capitalslearner.main.listfragment.model.InterceptionTypes
import com.osapps.capitalslearner.main.listfragment.presentation.narrator.CountriesNarrator
import com.osapps.capitalslearner.main.listfragment.presentation.narrator.CountriesNarratorCallback
import com.osapps.capitalslearner.main.listfragment.view.CountriesFragView

import javax.inject.Inject


/**
 * Created by osApps on 30/05/2017.
 */

class CountriesFragPresenterImpl @Inject constructor(private var view: CountriesFragView,
                                                     private var countriesNarrator: CountriesNarrator) : CountriesFragPresenter, CountriesNarratorCallback {

    @Inject lateinit var localRepository: LocalRepository
    lateinit var countriesList: ArrayList<CountryObj>


    override fun onViewLoaded() {
        countriesList = localRepository.getCountriesList(LocalRepositoryBank.KEY_ALL_COUNTRIES_LIST)
        countriesNarrator.setCallback(this)
        view.loadAdapter(countriesList)
        view.onListUpdated()
    }

    override fun onCountryAdded(countryObj: CountryObj) {
        countriesList.add(countryObj)
        localRepository.saveCountriesList(countriesList, LocalRepositoryBank.KEY_ALL_COUNTRIES_LIST)
        view.onListUpdated()
    }

    override fun removeCountry(countryObj: CountryObj) {
        countriesList.remove(countryObj)
        localRepository.saveCountriesList(countriesList, LocalRepositoryBank.KEY_ALL_COUNTRIES_LIST)
        view.onListUpdated()
    }

    override fun startRandomNarrationSequence() {
        countriesNarrator.runCycle(countriesList)
        view.popCardDialog()
    }

    override fun countriesList(): ArrayList<CountryObj>  =  countriesList



    override fun narratePreviousCountry() {
        countriesNarrator.narrateCountry()
    }

    override fun narratePreviousCapital() {
        countriesNarrator.narrateCapital()
    }

    override fun onCountryNarrated(countryObjPos: Int) {
        view.updateCardInput(InterceptionTypes.COUNTRY, countriesList[countryObjPos].country)
        //view.markNarratedCountry(countryObjPos)
        //view.toggleClickInterception(View.VISIBLE)
        //view.setClickInterceptionType(InterceptionTypes.COUNTRY)

    }

    override fun onCapitalNarrated(capitalObjPos: Int) {
        view.updateCardInput(InterceptionTypes.CAPITAL, countriesList[capitalObjPos].capital)
    }
    //override fun onCapitalNarrated(capitalObjPos: Int) = view.setClickInterceptionType(InterceptionTypes.CAPITAL)

    override fun onNarrationDone() = view.onNarrationDone()

    override fun narrateNextCapital() = countriesNarrator.narrateCapital()

    override fun narrateNextCountry() = countriesNarrator!!.loadNextNarration()



}
