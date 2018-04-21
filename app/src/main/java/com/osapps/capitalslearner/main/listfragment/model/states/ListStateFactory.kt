package com.osapps.capitalslearner.main.listfragment.model.states

import com.osapps.capitalslearner.main.listfragment.model.states.types.DefinitionState
import com.osapps.capitalslearner.main.listfragment.model.states.types.TranslationState

/**
 * Created by osapps on 21/04/2018.
 */
object ListStateFactory {
    enum class ListStateType {TRANSLATION_STYLE, DEFINITION_STYLE}

    fun createListState(type: ListStateType): ListState {

        return when(type){
            ListStateFactory.ListStateType.TRANSLATION_STYLE ->
                TranslationState()

            ListStateFactory.ListStateType.DEFINITION_STYLE ->
                DefinitionState()
        }

    }

}