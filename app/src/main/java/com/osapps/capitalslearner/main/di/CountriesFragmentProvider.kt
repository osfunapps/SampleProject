package com.osapps.capitalslearner.main.di

import com.osapps.capitalslearner.main.listfragment.di.CountriesFragmentModule
import com.osapps.capitalslearner.main.listfragment.view.ListFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by osApps on 02/06/2017.
 */
@Module
abstract class CountriesFragmentProvider {
    @ContributesAndroidInjector(modules = [CountriesFragmentModule::class]) //the specific module of the fragment
    internal abstract fun provideDetailFragmentFactory(): ListFragment
}
