package com.osapps.capitalslearner.main.listfragment.model.states

import android.app.Activity
import android.support.v4.app.FragmentActivity
import com.osapps.capitalslearner.infrastructure.LocalRepository
import com.osapps.capitalslearner.main.ListAdapter
import com.osapps.capitalslearner.main.listfragment.model.ListObj
import com.osapps.capitalslearner.main.listfragment.view.dialogs.types.*
import java.io.Serializable

/**
 * Created by osapps on 22/04/2018.
 */
const val TAB_SERIALIZABLE_ID: String = "listStateId"
interface ListState: Serializable {


    /**
     * returns the name of the tab
     */
    fun title(): String

    /**
     * will hold the key for all of the entries in the list
     */
    fun localRepoEntriesKey(): String

    /**
     * set the listObjects adapter
     */
    fun setAdapter()

    /*
    should return the adapter which extends the list adapter.
    //the adapter should hold a load() function which loads all of the parameters in it
    //val lst = listObjList as ArrayList<ListObj>
     */
    fun listAdapter(): ListAdapter

    /**
     * returns a list of list items which will populate the list
     */
    fun listOfObjList(localRepository: LocalRepository, hard: Boolean = false): ArrayList<ListObj>


    /**
     * set 3 dialogs
     */
    fun setAddDialog(activity: Activity)
    fun setRemoveDialog(activity: Activity)
    fun setCardDialog(activity: Activity)

    /**
     * return dialogs
     */
    fun addDialog(): AddListObjDialog
    fun cardDialog(): CardDialog

    /**
     * set dialogs callbacks
     */
    fun setRemoveDialogCallback(callback: RemoveListObjDialogCallback)
    fun setAddDialogCallback(callback: AddListObjDialogCallback)
    fun setCardDialogCallback(callback: CardDialogCallback)
    fun localRepoHardEntriesKey(): String

}