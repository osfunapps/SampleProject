package com.osapps.capitalslearner.main.presentation

import android.app.Activity
import com.osapps.capitalslearner.di.MainPresenter
import com.osapps.capitalslearner.main.listfragment.model.states.ListStateFactory
import com.osapps.capitalslearner.main.listfragment.model.states.ListStateType
import com.osapps.capitalslearner.main.model.TabObj


/** [MainActivityPresenterImpl] **/
interface MainActivityPresenter: MainPresenter {
    fun toSettings()
}
