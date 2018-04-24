package com.osapps.capitalslearner.main.listfragment.model.states

import android.app.Activity
import com.osapps.capitalslearner.main.listfragment.model.states.types.definition.DefinitionState
import com.osapps.capitalslearner.main.listfragment.model.states.types.translation.TranslationState
import java.lang.ref.WeakReference

/**
 * Created by osapps on 21/04/2018.
 */
object ListStateFactory {
    enum class ListStateType {TRANSLATION_STYLE, DEFINITION_STYLE}

    fun createListState(activity: Activity, type: ListStateType, name: String): ListState {

        val wra = WeakReference<Activity>(activity)
        return when(type){
            ListStateFactory.ListStateType.TRANSLATION_STYLE ->
                TranslationState(wra, name)

            ListStateFactory.ListStateType.DEFINITION_STYLE ->
                DefinitionState()
        }

    }

}