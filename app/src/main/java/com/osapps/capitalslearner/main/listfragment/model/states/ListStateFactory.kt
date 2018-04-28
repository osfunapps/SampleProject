package com.osapps.capitalslearner.main.listfragment.model.states

import com.osapps.capitalslearner.main.listfragment.model.states.types.definition.DefinitionState
import com.osapps.capitalslearner.main.listfragment.model.states.types.translation.TranslationState
import com.osapps.capitalslearner.main.model.TabObj
import java.io.Serializable

/**
 * Created by osapps on 21/04/2018.
 */

//the states types!
enum class ListStateType(var styleName: String) : Serializable {
    NO_STYLE("--Please Select --"),
    TRANSLATION_STYLE("One word definition style"),
    DEFINITION_STYLE("Question and answer explanation style");

    @Override
    override fun toString(): String  = styleName
}

object ListStateFactory {

    fun fromTabObjToState(tabObj: TabObj): ListState {
        val type = tabObj.type
        val name = tabObj.name

        return when(type){
            ListStateType.TRANSLATION_STYLE ->
                TranslationState(name)

            ListStateType.DEFINITION_STYLE ->
                DefinitionState(name)
            else -> { throw RuntimeException("You got no style!")
            }
        }

    }

}