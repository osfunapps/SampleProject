package com.osapps.capitalslearner.main.countries.view.dialogs

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog.show
import android.content.Context
import android.widget.EditText
import android.widget.TextView
import com.osapps.capitalslearner.R
import com.osapps.capitalslearner.R.id.country_remove_delete_tv
import com.osapps.capitalslearner.R.id.country_remove_title_tv
import com.osapps.capitalslearner.main.countries.model.CountryObj
import kotlinx.android.synthetic.main.dialog_remove_country.*

class RemoveCountryDialog

(activity: Activity, private val callback: RemoveCountryCallback) : Dialog(activity, R.style.FullHeightDialog) {


    init {
        setContentView(R.layout.dialog_remove_country)
        setCanceledOnTouchOutside(true)
        setOnClick()
    }


    fun buildAndRun(countryObj: CountryObj) {
        country_remove_title_tv.text = context.getString(R.string.remove_country_title, countryObj.country)
        country_remove_delete_tv.setOnClickListener {
            callback.onCountryRemoveRequested(countryObj)
            dismiss()
        }
        show()
    }


    private fun setOnClick() {
        country_remove_keep_tv.setOnClickListener { dismiss() }
    }

}

interface RemoveCountryCallback {
    fun onCountryRemoveRequested(countryObj: CountryObj)
}
