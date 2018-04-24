package com.osapps.capitalslearner.main.listfragment.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.osapps.capitalslearner.R
import com.osapps.capitalslearner.R.id.run_random_btn

import com.osapps.capitalslearner.main.listfragment.presentation.ListFragPresenter
import com.osapps.capitalslearner.main.listfragment.model.ListObj
import com.osapps.capitalslearner.main.listfragment.model.states.ListState
import com.osapps.capitalslearner.main.listfragment.model.states.STATE_SERIALIZABLE_ID

import javax.inject.Inject

import dagger.android.support.DaggerFragment
import com.osapps.capitalslearner.main.listfragment.view.dialogs.types.AddListObjDialogCallback
import com.osapps.capitalslearner.main.listfragment.view.dialogs.types.CardDialogCallback
import com.osapps.capitalslearner.main.listfragment.view.dialogs.types.RemoveListObjDialogCallback
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.translation_list_item.view.*
import kotlinx.android.synthetic.main.fragment_countries.*


/**
 * Created by osApps on 02/06/2017.
 */

class ListFragment : DaggerFragment(), ListFragView, AddListObjDialogCallback, RemoveListObjDialogCallback, CardDialogCallback {
    //list
    private lateinit var listView: RecyclerView

    //instances
    @Inject lateinit var presenter: ListFragPresenter


    lateinit var state: ListState

    private var clickInterceptionType: InterceptionTypes = InterceptionTypes.CARD_ONE

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setState()
        toolbar.title = state.title()
        setViews()
        setDialogsCallback()
        presenter.onViewLoaded() //list load, adapter load and more will be here
    }

    private fun setDialogsCallback() {
        state.setAddDialogCallback(this)
        state.setRemoveDialogCallback(this)
        state.setCardDialogCallback(this)
    }

    override fun loadAdapter(listObjList: ArrayList<in ListObj>?) {
        //load the adapter here
        state.listAdapter().load(listObjList)
    }


    private fun setViews() {

        //list view
        listView = view!!.findViewById<RecyclerView>(R.id.list_rv).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = state.listAdapter().get()
        }


        //fab clicker
        list_fab.setOnClickListener { state.addDialog().show() }
        //list_fab.setOnClickListener { addCountryDialog.show() }

        //random btn clicker
        run_random_btn.setOnClickListener {
            presenter.startRandomNarrationSequence()
            toggleRunBtnVisibility(View.GONE)
        }

        //click interceptor
        click_layout.setOnClickListener { onClickIntercepted() }
        click_layout.setOnLongClickListener { onLongClickIntercepted() }

    }


    override fun onListUpdated() = state.listAdapter().notifyDataSetChanged()

    override fun onAddItemChose(listObj: ListObj) = presenter.onListObjAdded(listObj)
    override fun onItemRemoveRequested(listObj: ListObj) = presenter.removeListObj(listObj)

    //todo: mark narrator
    override fun markNarratedCountry(narratedCountryIdx: Int) {
        listView.findViewHolderForAdapterPosition(narratedCountryIdx).itemView.list_item_papa.isSelected = true
    }



    override fun toggleClickInterception(visibility: Int) {
        click_layout.visibility = visibility
    }

    override fun setClickInterceptionType(type: InterceptionTypes) {
        clickInterceptionType = type
    }


    override fun onCardContentGeneratedObj(listObj: ListObj, cardPos: Int): String = state.cardDialog().displayCard(listObj, cardPos)


    private fun onClickIntercepted() {
        if (clickInterceptionType == InterceptionTypes.CARD_ONE)
            presenter.narrateNextCardTwo()
        else if (clickInterceptionType == InterceptionTypes.CARD_TWO) {
            //check if to disable from there the marking of the selected item (or leave it that way)
            presenter.narrateNextCardOne()
        }
    }

    private fun onLongClickIntercepted(): Boolean {
        if(clickInterceptionType == InterceptionTypes.CARD_ONE)
            presenter.narratePreviousCardOne()
        else
            presenter.narratePreviousCardTwo()
        return true
    }

    //todo: list marking
    private fun unMarkAllCountries() {
        val countriesCount = presenter.listOfObjList().size
        for (i in 0 until countriesCount) {
            listView.findViewHolderForAdapterPosition(i).itemView.list_item_papa.isSelected = false
        }
    }

    //todo: narration
    override fun onCardsDone() {
        state.cardDialog().dismiss()
        unMarkAllCountries()
        toggleRunBtnVisibility(View.VISIBLE)
    }

    private fun toggleRunBtnVisibility(visibility: Int) {
        run_random_btn.visibility = visibility
    }


    override fun popCardDialog() {
        if(!state.cardDialog().isShowing)
            state.cardDialog().show(InterceptionTypes.CARD_ONE)
    }

    override fun updateCardInput(interceptionType: InterceptionTypes, input: String) {
        state.cardDialog().updateInput(interceptionType, input)
    }


    override fun onCardClicked(interceptionType: InterceptionTypes) {
        when(interceptionType){
            InterceptionTypes.CARD_ONE -> presenter.narrateNextCardTwo()
            InterceptionTypes.CARD_TWO -> presenter.narrateNextCardOne()
        }
    }


    override fun onCardLongClicked(interceptionType: InterceptionTypes): Boolean {
        when (interceptionType) {
            InterceptionTypes.CARD_ONE -> presenter.narratePreviousCardOne()
            InterceptionTypes.CARD_TWO -> presenter.narratePreviousCardTwo()
        }
        return true
    }

    private fun setState() {
        state = arguments.getSerializable(STATE_SERIALIZABLE_ID) as ListState
        presenter.setState(state)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_countries, container, false)
    companion object {
        fun newInstance(listState: ListState): ListFragment {
            val listStateBundle = Bundle()
            listStateBundle.putSerializable(STATE_SERIALIZABLE_ID, listState)
            val lf = ListFragment()
            lf.arguments = listStateBundle
            return lf
        }
    }
}
