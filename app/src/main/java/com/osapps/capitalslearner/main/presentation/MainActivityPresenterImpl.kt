package com.osapps.capitalslearner.main.presentation

import android.app.Activity
import com.osapps.capitalslearner.infrastructure.LocalRepository
import com.osapps.capitalslearner.infrastructure.LocalRepositoryBank
import com.osapps.capitalslearner.main.model.TabObj
import com.osapps.capitalslearner.main.listfragment.model.states.ListStateFactory
import com.osapps.capitalslearner.main.presentation.tabs.TabsOrders
import com.osapps.capitalslearner.main.view.MainActivityView

import javax.inject.Inject


class MainActivityPresenterImpl @Inject constructor(var view: MainActivityView,
                                                    private val navigator: MainNavigator) : MainActivityPresenter {
    @Inject
    lateinit var localRepository: LocalRepository


    @Inject
    lateinit var tabsHandler: TabsOrders

    override fun onViewLoaded() {
        //view.callListFragment()
        tabsHandler.setCallbackView(view)
    }

    override fun getTabStripEntries(): Pair<Array<String>, Array<ListStateFactory.ListStateType>> = tabsHandler.getTabStripEntries()

    override fun onTabChanged(activity: Activity, name: String, type: ListStateFactory.ListStateType) {
        val listState = ListStateFactory.createListState(activity, type, name)
        navigator.toListFragment(listState)
    }

    //The TabStripHandler is instantiated from here and it takes the view as a callback.
    //this presenter will order it what to calculate and the output should be transfered to the mainView!


    /**todo: do the [AddTabDialog] here! **/
    override fun clickedAddTab() : Pair<Array<String>, Array<ListStateFactory.ListStateType>>{
        //pop "new tab dialog". It should return a title, a returned type, a "from" and a "to"
        //this is only an example of the returned object:
        val tabName = "Arabic Words"
        val listObj = TabObj(tabName, ListStateFactory.ListStateType.TRANSLATION_STYLE)
        val pairFromAndTo = Pair<String,String>("Arabic", "Hebrew")
        localRepository.saveObject(LocalRepositoryBank.KEY_FROM_TO + tabName, pairFromAndTo)
        return tabsHandler.addTab(listObj)
    }

}