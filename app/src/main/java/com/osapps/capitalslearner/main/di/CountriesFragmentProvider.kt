package com.osapps.capitalslearner.main.di

import com.osapps.capitalslearner.main.countries.di.CountriesFragmentModule
import com.osapps.capitalslearner.main.countries.view.CountriesFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by osApps on 02/06/2017.
 */
@Module
abstract class CountriesFragmentProvider {
    @ContributesAndroidInjector(modules = [CountriesFragmentModule::class]) //the specific module of the fragment
    internal abstract fun provideDetailFragmentFactory(): CountriesFragment
}
