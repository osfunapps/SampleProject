package com.osapps.capitalslearner.infrastructure

import com.osapps.capitalslearner.main.countries.model.CountryObj

interface LocalRepository{


    fun getString(key: String): String?
    fun getBool(key: String): Boolean
    fun saveBool(key: String, value: Boolean)
    fun save(key: String, value: String?)

    //var itz = localRepository.getObject(LocalRepositoryBank.KEY_1, ArrayList<CountryObj>()::class.java)
    fun <T> getObject(key: String, objType: Class<T>) : T?

    //.saveObject(LocalRepositoryBank.MY_KEY, ArrayList<CountryObj>()::class.java)
    fun <T> saveObject(key: String, value: T?)


    fun clear(key: String)

    //fun saveCountriesList(countriesList: ArrayList<CountryObj>, key: String)

    //fun getTabStripEntries() : Array<String>


    //fun saveTabStripEntries(tabStripEntries: Array<String>)
}