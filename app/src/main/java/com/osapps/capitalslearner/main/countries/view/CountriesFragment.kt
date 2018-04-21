package com.osapps.capitalslearner.main.countries.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.osapps.capitalslearner.R

import com.osapps.capitalslearner.main.countries.presentation.CountriesFragPresenter
import com.osapps.capitalslearner.main.countries.model.CountryObj
import com.osapps.capitalslearner.main.countries.model.InterceptionTypes

import javax.inject.Inject

import dagger.android.support.DaggerFragment
import com.osapps.capitalslearner.main.countries.presentation.list.CountriesAdapter
import com.osapps.capitalslearner.main.countries.view.dialogs.*
import kotlinx.android.synthetic.main.country_list_item.view.*
import kotlinx.android.synthetic.main.fragment_countries.*


/**
 * Created by osApps on 02/06/2017.
 */

class CountriesFragment : DaggerFragment(), CountriesFragView, AddCountryCallback, RemoveCountryCallback, CardDialogCallback {

    //list
    private lateinit var listView: RecyclerView
    @Inject lateinit var mAdapter: CountriesAdapter

    //instances
    @Inject lateinit var presenter: CountriesFragPresenter
    @Inject lateinit var addCountryDialog: AddCountryDialog
    @Inject lateinit var cardDialog: CardDialog


    private var clickInterceptionType: InterceptionTypes = InterceptionTypes.COUNTRY


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        presenter.onViewLoaded()

    }

    private fun setViews() {

        listView = view!!.findViewById<RecyclerView>(R.id.countries_rv).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
        }

        //fab clicker
        countries_fab.setOnClickListener { addCountryDialog.show() }

        //random btn clicker
        run_random_btn.setOnClickListener {
            presenter.startRandomNarrationSequence()
            toggleRunBtnVisibility(View.GONE)
        }

        //click interceptor
        click_layout.setOnClickListener { onClickIntercepted() }
        click_layout.setOnLongClickListener { onLongClickIntercepted() }

    }

    override fun loadAdapter(countriesList: ArrayList<CountryObj>) = mAdapter.setItems(countriesList)

    override fun onCountryChose(countryObj: CountryObj) = presenter.onCountryAdded(countryObj)

    override fun onListUpdated() = mAdapter.notifyDataSetChanged()

    override fun onCountryRemoveRequested(countryObj: CountryObj) = presenter.removeCountry(countryObj)

    override fun markNarratedCountry(narratedCountryIdx: Int) {
        listView.findViewHolderForAdapterPosition(narratedCountryIdx).itemView.list_item_papa.isSelected = true
    }



    override fun toggleClickInterception(visibility: Int) {
        click_layout.visibility = visibility
    }

    override fun setClickInterceptionType(type: InterceptionTypes) {
        clickInterceptionType = type
    }


    private fun onClickIntercepted() {
        if (clickInterceptionType == InterceptionTypes.COUNTRY)
            presenter.narrateNextCapital()
        else if (clickInterceptionType == InterceptionTypes.CAPITAL) {
            //check if to disable from there the marking of the selected item (or leave it that way)
            presenter.narrateNextCountry()
        }
    }

    private fun onLongClickIntercepted(): Boolean {
        if(clickInterceptionType == InterceptionTypes.COUNTRY)
            presenter.narratePreviousCountry()
        else
            presenter.narratePreviousCapital()
        return true
    }

    private fun unMarkAllCountries() {
        val countriesCount = presenter.countriesList().size
        for (i in 0 until countriesCount) {
            listView.findViewHolderForAdapterPosition(i).itemView.list_item_papa.isSelected = false
        }
    }


    override fun onNarrationDone() {
        cardDialog.dismiss()
        unMarkAllCountries()
        toggleRunBtnVisibility(View.VISIBLE)
        //toggleClickInterception(View.GONE)
    }

    private fun toggleRunBtnVisibility(visibility: Int) {
        run_random_btn.visibility = visibility
    }


    override fun popCardDialog() {
        if(!cardDialog.isShowing)
            cardDialog.show(InterceptionTypes.COUNTRY)
    }

    override fun updateCardInput(interceptionType: InterceptionTypes, input: String) {
        cardDialog.updateInput(interceptionType, input)
    }


    override fun onCardClicked(interceptionType: InterceptionTypes) {
        when(interceptionType){
            InterceptionTypes.COUNTRY -> presenter.narrateNextCapital()
            InterceptionTypes.CAPITAL -> presenter.narrateNextCountry()
        }
    }


    override fun onCardLongClicked(interceptionType: InterceptionTypes): Boolean {
        when (interceptionType) {
            InterceptionTypes.COUNTRY -> presenter.narratePreviousCountry()
            InterceptionTypes.CAPITAL -> presenter.narratePreviousCapital()
        }
        return true
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_countries, container, false)
    companion object { fun newInstance() = CountriesFragment() }
}
