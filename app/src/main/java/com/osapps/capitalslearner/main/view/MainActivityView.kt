package com.osapps.capitalslearner.main.view

import com.osapps.capitalslearner.di.MainView
import com.osapps.capitalslearner.main.model.ListObj

/** [MainActivity] **/
interface MainActivityView : MainView, TabsView{
    fun callCountriesListFragment()
    fun clickedSettings()
}
