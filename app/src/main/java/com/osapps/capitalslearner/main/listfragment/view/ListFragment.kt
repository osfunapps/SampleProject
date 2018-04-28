package com.osapps.capitalslearner.main.listfragment.view

import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.osapps.capitalslearner.R

import com.osapps.capitalslearner.main.listfragment.model.ListObj
import com.osapps.capitalslearner.main.listfragment.model.states.ListState
import com.osapps.capitalslearner.main.listfragment.model.states.ListStateType
import com.osapps.capitalslearner.main.listfragment.presentation.ListFragPresenter
import com.osapps.capitalslearner.main.listfragment.view.dialogs.types.*

import javax.inject.Inject

import dagger.android.support.DaggerFragment
import com.osapps.capitalslearner.main.model.TabObj
import com.osapps.capitalslearner.main.view.MainActivity
import com.osapps.capitalslearner.tools.ToolBarActivity
import com.osapps.capitalslearner.tools.extenstions.setViewsVisibility
import com.osapps.capitalslearner.tools.views.NavigationTabStrip
import kotlinx.android.synthetic.main.translation_list_item.view.*
import kotlinx.android.synthetic.main.fragment_list.*


/**
 * Created by osApps on 02/06/2017.
 */

class ListFragment : DaggerFragment(), ListFragView, AddListObjDialogCallback, RemoveListObjDialogCallback, CardDialogCallback {
    //instances
    private lateinit var listView: RecyclerView
    private lateinit var state: ListState
    @Inject lateinit var presenter: ListFragPresenter


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        list_fab.rippleColor = Color.WHITE;
        setTabStripOnClicks()
        presenter.setTabStrip()
        setOnClicks()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.findItem(R.id.action_add_tab)?.isVisible = true
    }

    //menu select
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.itemId) {
            R.id.action_add_tab -> {
                onAddTabClicked()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun setToolbar() {
        (activity!! as ToolBarActivity).toggleBackIcon(false)
        (activity!! as ToolBarActivity).setToolBarTitle(getString(R.string.app_name))
    }


    private fun setTabStripOnClicks() {
        tab_strip.onTabStripSelectedIndexListener = object : NavigationTabStrip.OnTabStripSelectedIndexListener{
            override fun onStartTabSelected(title: String, index: Int) {}
            override fun onTabSelected(title: String?, type: ListStateType?, index: Int) {
                val clickedTab = TabObj(title!!, type!!)
                presenter.onTabChanged(clickedTab)
            }
        }
    }

    override fun openLastClosedTab(lastCloseTabIdx: Int) = tab_strip.setTabIndex(lastCloseTabIdx, false)

    override fun stateReady(state: ListState) {
        this.state = state
        state.setAdapter()
        setViews()
        presenter.onStateViewsReady()
    }



    private fun onAddTabClicked() = AddTabDialog(activity!!, presenter).show()

    override fun onFinishAddTab(updatedTabs: Pair<Array<String>, Array<ListStateType>> ){
        refreshTabs(updatedTabs)
        openLastClosedTab(presenter.getLastClosedTabIdx())
    }

    override fun prepareViewWithListItems() {
        setViewsVisibility(View.GONE, add_list_obj)
        setViewsVisibility(View.VISIBLE, list_fab, run_random_btn)
    }

    override fun onCardClicked(cardContent: String) = presenter.drawNextCard()
    override fun onCardLongClicked(cardContent: String) = presenter.repeatCard()


    override fun refreshTabs(tabStripEntries: Pair<Array<String>, Array<ListStateType>>) {
        tab_strip.titles = tabStripEntries.first
        tab_strip.setTypes(tabStripEntries.second)
    }


    private fun setDialogs() {
        state.setAddDialog(activity!!)
        state.setCardDialog(activity!!)
        state.setRemoveDialog(activity!!)
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

        setDialogs()
        setDialogsCallback()

    }


    private fun setOnClicks() {
        //fab clicker
        list_fab.setOnClickListener { state.addDialog().show() }
        //list_fab.setOnClickListener { addCountryDialog.show() }

        //random btn clicker
        run_random_btn.setOnClickListener {
            if(presenter.listOfObjList().size != 0) {
                presenter.startRandomCycleSequence()
                setViewsVisibility(View.GONE, run_random_btn)
            }
        }

        //big add button
        add_list_obj.setOnClickListener { list_fab.callOnClick() }
        add_tab_big_iv.setOnClickListener { onAddTabClicked() }
    }

    override fun prepareViewWithoutTabs() {
        setViewsVisibility(View.VISIBLE, add_tab_big_iv)
        setViewsVisibility(View.GONE, list_fab, run_random_btn, add_list_obj)
    }

    override fun prepareViewWithoutListItems(){
        setViewsVisibility(View.GONE, run_random_btn, add_tab_big_iv, list_fab)
        setViewsVisibility(View.VISIBLE, add_list_obj)

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


    override fun onGenerateListObj(listObj: ListObj, cardPos: Int) {
        val strToNarrate = state.cardDialog().displayCardContent(listObj, cardPos)
        state.cardDialog().show()
        narrateWord(strToNarrate)
    }


    override fun onCardGeneratedObj(listObj: ListObj, cardPos: Int) {
        val strToNarrate = state.cardDialog().displayCardContent(listObj, cardPos)
        narrateWord(strToNarrate)
    }

    override fun narrateWord(strToNarrate: String) {
        MainActivity.mTTS?.speak(strToNarrate, TextToSpeech.QUEUE_ADD, null)
    }

    //todo: list marking
    private fun unMarkAllListObj() {
        val countriesCount = presenter.listOfObjList().size
        for (i in 0 until countriesCount) {
            listView.findViewHolderForAdapterPosition(i).itemView.list_item_papa.isSelected = false
        }
    }

    override fun onCardsDone() {
        state.cardDialog().dismiss()
        unMarkAllListObj()
        setViewsVisibility(View.VISIBLE, run_random_btn)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_list, container, false)

    companion object {
        fun newInstance(): ListFragment = ListFragment()
    }

    override fun onStop() {
        super.onStop()
        presenter.saveLastClosedTab(tab_strip.tabIndex)
    }

    override fun onCardDialogDismissed() {
        run_random_btn.visibility = View.VISIBLE
    }
}
