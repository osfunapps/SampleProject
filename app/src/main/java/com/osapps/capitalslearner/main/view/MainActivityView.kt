package com.osapps.capitalslearner.main.view

import android.support.v4.app.FragmentManager
import com.osapps.capitalslearner.di.MainView

/** [MainActivity] **/
interface MainActivityView : MainView, TabsView{
    fun clickedSettings()
    fun supportFragmentManager(): FragmentManager
}
