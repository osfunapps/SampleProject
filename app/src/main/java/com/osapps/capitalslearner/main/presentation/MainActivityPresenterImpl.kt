package com.osapps.capitalslearner.main.presentation

import com.osapps.capitalslearner.main.listfragment.view.ListFragment
import com.osapps.capitalslearner.main.view.MainActivityView

import javax.inject.Inject


class MainActivityPresenterImpl @Inject constructor(private var view: MainActivityView,
                                                    private val navigator: MainNavigator) : MainActivityPresenter {

    override fun toSettings() = navigator.toSettings()

    override fun onViewLoaded() = navigator.toListFragment()


}