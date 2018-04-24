package com.osapps.capitalslearner.main.view

import com.osapps.capitalslearner.di.MainView

/** [MainActivity] **/
interface MainActivityView : MainView, TabsView{
    fun clickedSettings()
}
