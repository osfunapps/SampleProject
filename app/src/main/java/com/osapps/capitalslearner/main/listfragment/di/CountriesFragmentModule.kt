package com.osapps.capitalslearner.main.listfragment.di

import com.osapps.capitalslearner.dagger.scope.PerFragment
import com.osapps.capitalslearner.main.listfragment.presentation.ListFragPresenter
import com.osapps.capitalslearner.main.listfragment.presentation.ListFragPresenterImpl
import com.osapps.capitalslearner.main.listfragment.presentation.list.TranslationStateAdapter
import com.osapps.capitalslearner.main.listfragment.presentation.narrator.Narrator
import com.osapps.capitalslearner.main.listfragment.view.ListFragment
import com.osapps.capitalslearner.main.listfragment.view.ListFragView
import com.osapps.capitalslearner.main.listfragment.view.dialogs.AddListObjDialog
import com.osapps.capitalslearner.main.listfragment.view.dialogs.StateTranslationCardDialog
import com.osapps.capitalslearner.main.listfragment.view.dialogs.StateTranslationRemoveListObjDialog

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
    internal fun provideCountriesFragmentView(countriesFragment: ListFragment): ListFragView = countriesFragment

    @Provides
    internal fun provideCountriesPresenterView(countriesFragPresenterImpl: ListFragPresenterImpl): ListFragPresenter = countriesFragPresenterImpl


    //list adapter
    @Provides
    internal fun provideAdapter(removeWord: StateTranslationRemoveListObjDialog): TranslationStateAdapter {
        return TranslationStateAdapter {
            removeWord.buildAndRun(it)
            true
        }
    }

    //add word dialog
    @Provides
    internal fun provideAddCountryDialog(countriesFragment: ListFragment): AddListObjDialog {
        return AddListObjDialog(countriesFragment.activity, countriesFragment)
    }

    //remove word dialog
    @Provides
    internal fun provideRemoveCountryDialog(countriesFragment: ListFragment): StateTranslationRemoveListObjDialog {
        return StateTranslationRemoveListObjDialog(countriesFragment.activity, countriesFragment)
    }

    //card dialog
    @Provides
    internal fun provideCardDialog(countriesFragment: ListFragment): StateTranslationCardDialog {
        return StateTranslationCardDialog(countriesFragment.activity, countriesFragment)
    }

    //narrator
    @Provides
    internal fun provideCountriesNarrator(): Narrator {
        return Narrator()
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
