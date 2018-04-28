package com.osapps.capitalslearner.infrastructure

import com.osapps.capitalslearner.main.listfragment.model.states.types.translation.TranslationListObj
import com.osapps.capitalslearner.main.model.TabObj
import kotlin.reflect.KClass

interface LocalRepository{


    //getters
    fun getString(key: String): String?
    fun getBool(key: String): Boolean
    fun getInt(key: String): Int

    //setters
    fun saveBool(key: String, value: Boolean)
    fun saveString(key: String, value: String?)
    fun saveInt(key: String, int: Int)

    //generics
    fun <T> saveObject(key: String, value: T?)
    fun <T> getArrayListObject(key: String, clazz: Class<T>) : ArrayList<T>?
    fun <T, E> getPair(key: String, firstObj: Class<T>, secondObj: Class<E>): Pair<T, E>?

    //cleaner
    fun removeKey(key: String)
}