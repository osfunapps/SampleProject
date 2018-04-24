package com.osapps.capitalslearner.main.listfragment.model.states

import com.osapps.capitalslearner.infrastructure.LocalRepository
import com.osapps.capitalslearner.main.ListAdapter
import com.osapps.capitalslearner.main.listfragment.model.ListObj
import com.osapps.capitalslearner.main.listfragment.view.dialogs.StateTranslationCardDialog
import com.osapps.capitalslearner.main.listfragment.view.dialogs.types.*
import java.io.Serializable

/**
 * Created by osapps on 22/04/2018.
 */
const val STATE_SERIALIZABLE_ID: String = "listStateId"
interface ListState: Serializable {


    /**
     * returns the title of the tab
     */
    fun title(): String

    /**
     * will initiated all of the instances in the current state
     */
    fun init()

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
    //val lst = listObjList as ArrayList<TranslationListObj>
     */
    fun listAdapter(): ListAdapter

    /**
     * returns a list of list items which will populate the list
     */
    fun listOfObjList(localRepository: LocalRepository): ArrayList<in ListObj>


    /**
     * returns the key to the local repo of the list
     */
    fun localRepoKey(): String


    /**
     * set 3 dialogs
     */
    fun setAddDialog()
    fun setRemoveDialog()
    fun setCardDialog()

    /**
     * return dialogs
     */
    fun addDialog(): AddListObjDialog
    fun cardDialog(): CardDialog
    fun removeDialog(): RemoveListObjDialog


    /**
     * set dialogs callbacks
     */
    fun setRemoveDialogCallback(listCallback: RemoveListObjDialogCallback)
    fun setAddDialogCallback(listCallback: AddListObjDialogCallback)
    fun setCardDialogCallback(cardDialogCallback: CardDialogCallback)

}