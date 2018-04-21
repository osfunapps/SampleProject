package com.osapps.capitalslearner.main.presentation.tabs

import com.osapps.capitalslearner.infrastructure.LocalRepository
import com.osapps.capitalslearner.infrastructure.LocalRepositoryBank
import com.osapps.capitalslearner.main.model.ListObj
import com.osapps.capitalslearner.main.model.ListObjFactory
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
    private val FIRST_TAB_DEF_TYPE: ListObjFactory.ListObjType = ListObjFactory.ListObjType.DEFINITION_STYLE

    //This method meant to undress the tabs titles from the list of ListObjects.
    //(Every ListObj holds the type and the title so this method meant to strip the titles from all).
    fun setTitles(tabStripEntries: ArrayList<ListObj>){

    }


    override fun getTabStripEntries(): Pair<Array<String>, Array<ListObjFactory.ListObjType>> {
        var tabStripEntries = loadTabs()
        //if it's the first time the app loads, we will save the first pair
        if(tabStripEntries == null) {
            tabStripEntries = getFirstTab()
            saveTabs(tabStripEntries)
        }

        return tabsAsPairs(tabStripEntries)
    }

    override fun addTab(listObj: ListObj): Pair<Array<String>, Array<ListObjFactory.ListObjType>> {
        //get the list of objects from the local repo
        val tabStripEntries = loadTabs()!!

        //add the object and save the new list
        tabStripEntries.add(listObj)
        saveTabs(tabStripEntries)
        return tabsAsPairs(tabStripEntries)
    }


    private fun tabsAsPairs(tabStripEntries: ArrayList<ListObj>): Pair<Array<String>, Array<ListObjFactory.ListObjType>> {
        val tabStripTitles = arrayOf<String>()
        val tabStripTypes = arrayOf<ListObjFactory.ListObjType>()
        for (i in 0 until tabStripEntries.size) {
            tabStripTitles[i] = tabStripEntries[i].title
            tabStripTypes[i] = tabStripEntries[i].type
        }

        return Pair(tabStripTitles, tabStripTypes)

    }

    private fun getFirstTab(): ArrayList<ListObj> {
        val firstTabList = ArrayList<ListObj>()
        firstTabList.add(ListObj(FIRST_TAB_DEF_NAME, FIRST_TAB_DEF_TYPE))
        return firstTabList
    }

    private fun loadTabs(): ArrayList<ListObj>? = localRepository.getObject(
            LocalRepositoryBank.KEY_ALL_COUNTRIES_LIST,
            ArrayList<ListObj>()::class.java)

    private fun saveTabs(firstTabList: ArrayList<ListObj>) = localRepository.saveObject(LocalRepositoryBank.KEY_TABS_STRIP_ENTRIES, firstTabList)




    override fun setCallbackView(view: TabsView) {
        callback = view
    }
}

//this callback should report back to main activity view!
interface TabsView{
    fun onTabChanged(position: Int, name: String)

}