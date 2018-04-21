package com.osapps.capitalslearner.main.presentation

import com.osapps.capitalslearner.di.MainPresenter
import com.osapps.capitalslearner.main.model.ListObjFactory


/** [MainActivityPresenterImpl] **/
interface MainActivityPresenter: MainPresenter {
    fun getTabStripEntries(): Pair<Array<String>, Array<ListObjFactory.ListObjType>>
    fun onTabChanged(index: String, type: ListObjFactory.ListObjType)
    fun clickedAddTab() :Pair<Array<String>, Array<ListObjFactory.ListObjType>>

}
