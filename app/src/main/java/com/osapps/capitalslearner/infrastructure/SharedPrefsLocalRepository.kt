package com.osapps.capitalslearner.infrastructure

import android.content.SharedPreferences
import com.osapps.capitalslearner.main.countries.model.CountryObj
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.collections.HashSet


class SharedPrefsLocalRepository(private val preferences: SharedPreferences) : LocalRepository {
    override fun save(key: String, value: String?) {
        preferences.edit().putString(key, value).apply()
    }

    override fun saveBool(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    override fun getString(key: String): String? {
        return preferences.getString(key, null)
    }
    override fun getBool(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    override fun clear(key: String) {
        preferences.edit().remove(key).apply()
    }

    override fun <T> getObject(key: String, objType: Class<T>): T? {
        val json = preferences.getString(key, "")
        if(json != "") {
            val type = object : TypeToken<T>() {}.type
            return Gson().fromJson<T>(json, type)
        }
        return null
    }

    override fun <T> saveObject(key: String, value: T?) {
        val json = Gson().toJson(value)
        preferences.edit().putString(key, json).apply()
    }

    /*override fun <T> get(key: String, objType: T): T? {
        val json = preferences.getString(key, "")
        if(json != "") {
            val type = object : TypeToken<T>() {}.type
            return Gson().fromJson<T>(json, type)
        }
        return null
    }
*/

 /*   override fun getCountriesList(key: String): ArrayList<CountryObj> {
        val json = preferences.getString(key, "")
        if(json != "") {
            val type = object : TypeToken<ArrayList<CountryObj>>() {}.type
            return Gson().fromJson<ArrayList<CountryObj>>(json, type)
        }
        return ArrayList<CountryObj>()
    }
*/


}