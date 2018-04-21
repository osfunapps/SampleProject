package com.osapps.capitalslearner.main.presentation

import com.osapps.capitalslearner.infrastructure.LocalRepository
import com.osapps.capitalslearner.infrastructure.LocalRepositoryBank
import com.osapps.capitalslearner.main.model.ListObj
import com.osapps.capitalslearner.main.model.ListObjFactory
import com.osapps.capitalslearner.main.presentation.tabs.TabsOrders
import com.osapps.capitalslearner.main.view.MainActivityView
import com.osapps.capitalslearner.tools.extensions.views.NavigationTabStrip

import javax.inject.Inject


class MainActivityPresenterImpl @Inject constructor(var view: MainActivityView) : MainActivityPresenter {
    @Inject
    lateinit var localRepository: LocalRepository


    @Inject
    lateinit var tabsHandler: TabsOrders

    override fun onViewLoaded() {
        //view.callCountriesListFragment()
        tabsHandler.setCallbackView(view)
    }

    override fun getTabStripEntries(): Pair<Array<String>, Array<ListObjFactory.ListObjType>> = tabsHandler.getTabStripEntries()

    override fun onTabChanged(index: String, type: ListObjFactory.ListObjType) {
        //send the type to the factory with "When" method.. bla blla bla continue from here!
    }

    //The TabStripHandler is instantiated from here and it takes the view as a callback.
    //this presenter will order it what to calculate and the output should be transfered to the mainView!


    /**todo: do the [AddTabDialog] here! **/
    override fun clickedAddTab() : Pair<Array<String>, Array<ListObjFactory.ListObjType>>{
        //pop "new tab dialog". It should return a title and a type
        //this is only an example of the returned object:
        val listObj = ListObj("Persian", ListObjFactory.ListObjType.TRANSLATION_STYLE)
        return tabsHandler.addTab(listObj)
    }

}