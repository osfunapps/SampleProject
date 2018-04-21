package com.osapps.capitalslearner.main.listfragment.view.dialogs

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.support.v4.content.ContextCompat
import com.osapps.capitalslearner.R
import com.osapps.capitalslearner.main.listfragment.model.InterceptionTypes
import kotlinx.android.synthetic.main.dialog_card.*

class CardDialog (activity: Activity, private val callback: CardDialogCallback) : Dialog(activity, android.R.style.Theme_Light_NoTitleBar_Fullscreen) {

    //instances
    private lateinit var interceptionType: InterceptionTypes

    init {
        setContentView(R.layout.dialog_card)
        setOnClick()
    }


    fun show(interceptionType: InterceptionTypes) {
        this.interceptionType = interceptionType
        super.show()
    }

    private fun setOnClick() {
        papa_view.setOnClickListener { callback.onCardClicked(interceptionType) }
        papa_view.setOnLongClickListener { callback.onCardLongClicked(interceptionType) }
    }


    override fun show() { throw RuntimeException("call show(countryObj: CountryObj)!") }

    fun updateInput(interceptionType: InterceptionTypes, input: String) {
        this.interceptionType = interceptionType
        card_input_type.text = interceptionType.name

        if(interceptionType == InterceptionTypes.COUNTRY) {
            card_input_name.setTextColor(Color.BLUE)
            card_input_name.text = input
            card_input_type.text = context.getString(R.string.country)
        } else {
            card_input_name.setTextColor(ContextCompat.getColor(context, R.color.tomato))
            card_input_name.text = input
            card_input_type.text = context.getString(R.string.capital)
        }
    }
}


    interface CardDialogCallback {
    fun onCardClicked(interceptionType: InterceptionTypes)
    fun onCardLongClicked(interceptionType: InterceptionTypes): Boolean

}
