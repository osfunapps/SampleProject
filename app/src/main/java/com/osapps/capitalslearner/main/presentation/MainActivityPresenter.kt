package com.osapps.capitalslearner.main.presentation

import android.app.Activity
import com.osapps.capitalslearner.di.MainPresenter
import com.osapps.capitalslearner.main.listfragment.model.states.ListStateFactory


/** [MainActivityPresenterImpl] **/
interface MainActivityPresenter: MainPresenter {
    fun getTabStripEntries(): Pair<Array<String>, Array<ListStateFactory.ListStateType>>
    fun onTabChanged(activity: Activity, name: String, type: ListStateFactory.ListStateType)
    fun clickedAddTab() :Pair<Array<String>, Array<ListStateFactory.ListStateType>>

}
