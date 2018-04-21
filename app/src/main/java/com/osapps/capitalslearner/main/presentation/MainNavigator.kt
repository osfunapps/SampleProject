package com.osapps.capitalslearner.main.presentation

import android.content.Context
import com.osapps.capitalslearner.R
import com.osapps.capitalslearner.main.listfragment.model.states.ListState
import com.osapps.capitalslearner.main.listfragment.view.ListFragment
import com.osapps.capitalslearner.main.view.MainActivity
import com.osapps.capitalslearner.tools.extensions.addFragment

class MainNavigator(internal val activity: MainActivity, internal val context: Context) {

    fun toListFragment(listState: ListState) {
        activity.addFragment("listFragment", R.id.fragment_container) {
            ListFragment.newInstance(listState)
        }
    }
}