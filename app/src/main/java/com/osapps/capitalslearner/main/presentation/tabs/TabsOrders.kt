package com.osapps.capitalslearner.main.presentation.tabs

import com.osapps.capitalslearner.main.model.ListObj
import com.osapps.capitalslearner.main.listfragment.model.states.ListStateFactory
import com.osapps.capitalslearner.main.view.TabsView

/**
 * Created by osapps on 21/04/2018.
 */
interface TabsOrders {
    fun setCallbackView(view: TabsView)
    fun getTabStripEntries(): Pair<Array<String>, Array<ListStateFactory.ListStateType>>
    fun addTab(listObj: ListObj) :Pair<Array<String>, Array<ListStateFactory.ListStateType>>

}