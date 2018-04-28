package com.osapps.capitalslearner.main.presentation

import com.osapps.capitalslearner.R
import com.osapps.capitalslearner.itzik.extenstions.addFragmentBackStack
import com.osapps.capitalslearner.main.view.MainActivity
import com.osapps.capitalslearner.itzik.extenstions.replaceFragment
import com.osapps.capitalslearner.main.listfragment.view.ListFragment
import com.osapps.capitalslearner.main.settingsfragment.view.TabsManagerFrag

class MainNavigator(internal val activity: MainActivity) {

    fun toListFragment() {
        activity.replaceFragment(Finals.TAG_FRAGMENT_LIST, R.id.fragment_container) {
            ListFragment.newInstance()
        }
    }

    fun toSettings() {
        activity.addFragmentBackStack(Finals.TAG_FRAGMENT_TABS_MANAGER, R.id.fragment_container) {
            TabsManagerFrag.newInstance()
        }
    }
}