package com.osapps.capitalslearner.main.listfragment.presentation.narrator

import android.speech.tts.TextToSpeech
import com.osapps.capitalslearner.main.listfragment.model.CountryObj
import com.osapps.capitalslearner.main.view.MainActivity
import java.util.*


/**
 * Created by osapps on 19/04/2018.
 */
class CountriesNarrator {

    private var countriesCount: Int = 0
    private lateinit var wordsIndexesChoseList: ArrayList<Int>
    private var nextCountryIdx: Int = 0
    private lateinit var callback: CountriesNarratorCallback

    private lateinit var countriesList: ArrayList<CountryObj>

    fun setCallback(callback: CountriesNarratorCallback){ this.callback = callback }

    fun runCycle(countriesList: ArrayList<CountryObj>) {
        this.countriesList = countriesList
        countriesCount = countriesList.size
        wordsIndexesChoseList = ArrayList()

        //fill an array list as 0,1,2,3,4..countriesCount
        for (i in 0 until countriesCount)
            wordsIndexesChoseList.add(i)

        loadNextNarration()
    }



    fun loadNextNarration() {
        if (wordsIndexesChoseList.isEmpty()) {
            callback.onNarrationDone()
            return
        }

        //keep rolling the dice until you'll find an index you haven't used before
        val randomCountryIdx = Random().nextInt(wordsIndexesChoseList.size)
        nextCountryIdx = wordsIndexesChoseList[randomCountryIdx]
        wordsIndexesChoseList.removeAt(randomCountryIdx)
        narrateCountry()

    }

    fun narrateCountry() {
        val country = countriesList[nextCountryIdx].country
        MainActivity.mTTS?.speak(country, TextToSpeech.QUEUE_ADD, null);
        callback.onCountryNarrated(nextCountryIdx)

    }

    fun narrateCapital() {
        val capital = countriesList[nextCountryIdx].capital
        MainActivity.mTTS?.speak(capital, TextToSpeech.QUEUE_ADD, null);
        callback.onCapitalNarrated(nextCountryIdx)
    }

}