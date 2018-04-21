package com.osapps.capitalslearner.main.listfragment.di

import com.osapps.capitalslearner.dagger.scope.PerFragment
import com.osapps.capitalslearner.main.listfragment.presentation.CountriesFragPresenter
import com.osapps.capitalslearner.main.listfragment.presentation.CountriesFragPresenterImpl
import com.osapps.capitalslearner.main.listfragment.presentation.list.CountriesAdapter
import com.osapps.capitalslearner.main.listfragment.presentation.narrator.CountriesNarrator
import com.osapps.capitalslearner.main.listfragment.view.ListFragment
import com.osapps.capitalslearner.main.listfragment.view.CountriesFragView
import com.osapps.capitalslearner.main.listfragment.view.dialogs.AddCountryDialog
import com.osapps.capitalslearner.main.listfragment.view.dialogs.CardDialog
import com.osapps.capitalslearner.main.listfragment.view.dialogs.RemoveCountryDialog

import dagger.Module
import dagger.Provides

/**
 * Created by osApps on 02/06/2017.
 */

/**
 * This module holds all of the instances related to DetailFragment.
 * In order to inject specific instance into the fragment, we need to:
 * [1] make sure that we provide all of the dependencies of the instance in this module (you can check
 * out AppModule's sharedPreferences initialization for a good example)
 * [2] the constructor of the implemented instance should annotate itself with @inject
 * [3] add the instance to the constructor of the instance
 */
@Module
@PerFragment
class CountriesFragmentModule {

    @Provides
    internal fun provideCountriesFragmentView(countriesFragment: ListFragment): CountriesFragView = countriesFragment

    @Provides
    internal fun provideCountriesPresenterView(countriesFragPresenterImpl: CountriesFragPresenterImpl): CountriesFragPresenter = countriesFragPresenterImpl


    //list adapter
    @Provides
    internal fun provideAdapter(removeCountryDialog: RemoveCountryDialog): CountriesAdapter {
        return CountriesAdapter {
            removeCountryDialog.buildAndRun(it)
            true
        }
    }

    //add country dialog
    @Provides
    internal fun provideAddCountryDialog(countriesFragment: ListFragment): AddCountryDialog {
        return AddCountryDialog(countriesFragment.activity, countriesFragment)
    }

    //remove country dialog
    @Provides
    internal fun provideRemoveCountryDialog(countriesFragment: ListFragment): RemoveCountryDialog {
        return RemoveCountryDialog(countriesFragment.activity, countriesFragment)
    }

    //card dialog
    @Provides
    internal fun provideCardDialog(countriesFragment: ListFragment): CardDialog {
        return CardDialog(countriesFragment.activity, countriesFragment)
    }

    //narrator
    @Provides
    internal fun provideCountriesNarrator(): CountriesNarrator {
        return CountriesNarrator()
    }




//    @Provides
//    internal fun provideCountriesNarrator2(countriesFragPresenterImpl: CountriesFragPresenterImpl): CountriesNarratorCallback { return countriesFragPresenterImpl }
//
//    //narrator
//    @Provides
//    internal fun provideCountriesNarrator(presenterImpl: CountriesFragPresenter): CountriesNarrator {
//        return CountriesNarrator(presenterImpl)
//    }


}
