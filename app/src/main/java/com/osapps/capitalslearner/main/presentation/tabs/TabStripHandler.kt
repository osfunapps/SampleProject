package com.osapps.capitalslearner.main.presentation.tabs

import com.osapps.capitalslearner.infrastructure.LocalRepository
import com.osapps.capitalslearner.infrastructure.LocalRepositoryBank
import com.osapps.capitalslearner.main.model.TabObj
import com.osapps.capitalslearner.main.listfragment.model.states.ListStateFactory
import com.osapps.capitalslearner.main.view.TabsView
import javax.inject.Inject

/**
 * Created by osapps on 20/04/2018.
 */
class TabStripHandler: TabsOrders {

    @Inject
    lateinit var localRepository: LocalRepository

    private lateinit var callback: TabsView

    //first tab styles
    private val FIRST_TAB_DEF_NAME: String = "Titles"
    private val FIRST_TAB_DEF_TYPE: ListStateFactory.ListStateType = ListStateFactory.ListStateType.DEFINITION_STYLE

    //This method meant to undress the tabs titles from the list of ListObjects.
    //(Every ListObj holds the type and the title so this method meant to strip the titles from all).
    fun setTitles(tabStripEntries: ArrayList<TabObj>){

    }


    override fun getTabStripEntries(): Pair<Array<String>, Array<ListStateFactory.ListStateType>> {
        var tabStripEntries = loadTabs()
        //if it's the first time the app loads, we will save the first pair
        if(tabStripEntries == null) {
            tabStripEntries = getFirstTab()
            saveTabs(tabStripEntries)
        }

        return tabsAsPairs(tabStripEntries)
    }

    override fun addTab(listObj: TabObj): Pair<Array<String>, Array<ListStateFactory.ListStateType>> {

        //get the list of objects from the local repo
        val tabStripEntries = loadTabs()!!

        //add the object and save the new list
        tabStripEntries.add(listObj)
        saveTabs(tabStripEntries)
        return tabsAsPairs(tabStripEntries)
    }


    private fun tabsAsPairs(tabStripEntries: ArrayList<TabObj>): Pair<Array<String>, Array<ListStateFactory.ListStateType>> {
        val tabStripTitles = arrayOf<String>()
        val tabStripTypes = arrayOf<ListStateFactory.ListStateType>()
        for (i in 0 until tabStripEntries.size) {
            tabStripTitles[i] = tabStripEntries[i].title
            tabStripTypes[i] = tabStripEntries[i].type
        }

        return Pair(tabStripTitles, tabStripTypes)

    }

    private fun getFirstTab(): ArrayList<TabObj> {
        val firstTabList = ArrayList<TabObj>()
        firstTabList.add(TabObj(FIRST_TAB_DEF_NAME, FIRST_TAB_DEF_TYPE))
        return firstTabList
    }

    private fun loadTabs(): ArrayList<TabObj>? = localRepository.getObject(
            LocalRepositoryBank.KEY_ALL_COUNTRIES_LIST,
            ArrayList<TabObj>()::class.java)

    private fun saveTabs(tabsList: ArrayList<TabObj>) = localRepository.saveObject(LocalRepositoryBank.KEY_TABS_STRIP_ENTRIES, tabsList)




    override fun setCallbackView(view: TabsView) {
        callback = view
    }
}

//this callback should report back to main activity view!
interface TabsView{
    fun onTabChanged(position: Int, name: String)

}