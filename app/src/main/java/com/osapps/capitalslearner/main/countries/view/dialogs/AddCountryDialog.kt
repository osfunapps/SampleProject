package com.osapps.capitalslearner.main.countries.view.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.osapps.capitalslearner.R
import com.osapps.capitalslearner.main.countries.model.CountryObj
import kotlinx.android.synthetic.main.country_list_item.*
import kotlinx.android.synthetic.main.dialog_add_country.*
import javax.inject.Inject
class AddCountryDialog (activity: Activity, private val callback: AddCountryCallback) : Dialog(activity, R.style.FullHeightDialog) {



    init {
        setContentView(R.layout.dialog_add_country)
        setCanceledOnTouchOutside(true)
        country_name_choose_et.viewTreeObserver.addOnDrawListener{country_name_choose_et.maxWidth = country_name_choose_et.width}
        capital_name_choose_et.viewTreeObserver.addOnDrawListener{capital_name_choose_et.maxWidth = capital_name_choose_et.width}
        setOnClick()
    }


    private fun setOnClick() {
        country_add_go_btn.setOnClickListener {onExitClicked() }
    }

    private fun onExitClicked() {
        if (!country_name_choose_et.text.equals("") && !capital_name_choose_et!!.text.equals(""))
            callback.onCountryChose(CountryObj(country_name_choose_et.text.toString(), capital_name_choose_et.text.toString()))
        dismiss()

    }

    override fun show() {
        openKeyboard()
        super.show()
    }

    override fun dismiss() {
        closeKeyboard()
        super.dismiss()
    }

    private fun closeKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
    }

    private fun openKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }

}

interface AddCountryCallback {
    fun onCountryChose(countryObj: CountryObj)
}
