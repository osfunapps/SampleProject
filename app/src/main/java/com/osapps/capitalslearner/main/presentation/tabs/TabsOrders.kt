package com.osapps.capitalslearner.main.presentation.tabs

import com.osapps.capitalslearner.main.model.ListObj
import com.osapps.capitalslearner.main.model.ListObjFactory
import com.osapps.capitalslearner.main.view.TabsView
import com.osapps.capitalslearner.tools.extensions.views.NavigationTabStrip

/**
 * Created by osapps on 21/04/2018.
 */
interface TabsOrders {
    fun setCallbackView(view: TabsView)
    fun getTabStripEntries(): Pair<Array<String>, Array<ListObjFactory.ListObjType>>
    fun addTab(listObj: ListObj) :Pair<Array<String>, Array<ListObjFactory.ListObjType>>

}