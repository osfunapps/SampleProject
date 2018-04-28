package com.osapps.capitalslearner.main.presentation.tabs

import com.osapps.capitalslearner.infrastructure.LocalRepository
import com.osapps.capitalslearner.infrastructure.LocalRepositoryBank
import com.osapps.capitalslearner.main.model.TabObj
import com.osapps.capitalslearner.main.listfragment.model.states.ListStateType
import com.osapps.capitalslearner.main.view.TabsView
import javax.inject.Inject

/**
 * Created by osapps on 20/04/2018.
 */
class TabStripHandler @Inject constructor(var localRepository: LocalRepository) : TabsOrders {


    //first tab styles
    /*private val FIRST_TAB_DEF_NAME: String = "Persian Words"
    private val FIRST_TAB_DEF_TYPE: ListStateType = ListStateType.TRANSLATION_STYLE
    private val FIRST_TAB_DEF_FROM: String = "Persian"
    private val FIRST_TAB_DEF_TO: String = "Hebrew"
*/
    //This method meant to undress the tabs titles from the list of ListObjects.
    //(Every ListObj holds the type and the name so this method meant to strip the titles from all).
    fun setTitles(tabStripEntries: ArrayList<TabObj>) {

    }


    override fun getTabStripEntries(): Pair<Array<String>, Array<ListStateType>>? {
        var tabStripEntries = loadTabs()
        //if it's the first time the app loads, we will saveString the first pair
        if (tabStripEntries == null) return null
        return tabsAsPairs(tabStripEntries)
    }


    override fun addTab(tabObj: TabObj): Pair<Array<String>, Array<ListStateType>> {

        //get the list of objects from the local repo
        var tabStripEntries = loadTabs()
        if(tabStripEntries == null) {
            tabStripEntries = ArrayList<TabObj>()
        }

        //add the object and saveString the new list
        tabStripEntries.add(tabObj)

        saveTabs(tabStripEntries)
        saveLastClosedTab(tabStripEntries.size)
        return tabsAsPairs(tabStripEntries)
    }

    private fun saveLastClosedTab(tabIdx: Int) = localRepository.saveInt(LocalRepositoryBank.KEY_LAST_CLOSED_TAB, tabIdx)


    /**
     * divide the list of tabs to names and types lists
     */
    private fun tabsAsPairs(tabStripEntries: ArrayList<TabObj>): Pair<Array<String>, Array<ListStateType>> {
        val tabsCount = tabStripEntries.size
        val tabStripTitles = Array<String>(tabsCount) { "" }
        val tabStripTypes = Array<ListStateType>(tabsCount) { ListStateType.TRANSLATION_STYLE }
        for (i in 0 until tabStripEntries.size) {
            tabStripTitles[i] = tabStripEntries[i].name
            tabStripTypes[i] = tabStripEntries[i].type
        }

        return Pair(tabStripTitles, tabStripTypes)


    }

    private fun loadTabs(): ArrayList<TabObj>? = localRepository.getArrayListObject(
            LocalRepositoryBank.KEY_TABS_STRIP_ENTRIES, TabObj::class.java)

    private fun saveTabs(tabsList: ArrayList<TabObj>) = localRepository.saveObject(LocalRepositoryBank.KEY_TABS_STRIP_ENTRIES, tabsList)

}