package com.osapps.capitalslearner.main.view

import android.os.Bundle

import com.osapps.capitalslearner.R
import com.osapps.capitalslearner.main.presentation.MainActivityPresenter

import javax.inject.Inject

import dagger.android.support.DaggerAppCompatActivity
import android.speech.tts.TextToSpeech
import android.content.Intent
import android.support.v4.view.ViewPager
import android.view.Menu
import android.widget.Toast
import com.osapps.capitalslearner.tools.extensions.views.NavigationTabStrip
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.view.MenuItem
import com.osapps.capitalslearner.main.listfragment.model.states.ListStateFactory
import com.osapps.capitalslearner.main.presentation.tabs.TabStripHandler


private const val ACT_CHECK_TTS_DATA: Int = 999

class MainActivity : DaggerAppCompatActivity(), MainActivityView, TextToSpeech.OnInitListener {

    @Inject
    lateinit var presenter: MainActivityPresenter

    @Inject
    lateinit var tabStripHandler: TabStripHandler


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))


        runNarratorIntent()

        //added
        setOnTabsClick()
    }

    private fun setOnTabsClick() {
    }


    private fun onPageChangeListener(): ViewPager.OnPageChangeListener? {
        val simpleListener = ViewPager.SimpleOnPageChangeListener()
        return simpleListener

    }


    //menu inflation
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    //menu select
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.itemId) {
            R.id.action_add_tab -> {
                val tabs = presenter.clickedAddTab()
                refreshTabs(tabs)
                return true
            }
            R.id.settings -> {
                clickedSettings()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun refreshTabs(tabStripEntries: Pair<Array<String>, Array<ListStateFactory.ListStateType>>) {
        tab_strip.titles = tabStripEntries.first
        tab_strip.setTypes(tabStripEntries.second)
    }

    //should be in different activity. the user should not see that tabs when he click here!
    override fun clickedSettings() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    //todo: remove redundant stuff from here!
    private fun runNarratorIntent() {
        val ttsIntent = Intent()
        ttsIntent.action = TextToSpeech.Engine.ACTION_CHECK_TTS_DATA
        startActivityForResult(ttsIntent, ACT_CHECK_TTS_DATA)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == ACT_CHECK_TTS_DATA) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // Data exists, so we instantiate the TTS engine
                mTTS = TextToSpeech(this, this)
            } else {
                // Data is missing, so we start the TTS installation
                // process
                val installIntent = Intent()
                installIntent.action = TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA
                startActivity(installIntent)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            if (mTTS != null) {
                val result = mTTS!!.setLanguage(Locale.US)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(this, "TTS language is not supported",
                            Toast.LENGTH_LONG).show()
                } else {
                    setTabStrip()
                    presenter.onViewLoaded()
                }
            }
        } else {
            Toast.makeText(this, "TTS initialization failed",
                    Toast.LENGTH_LONG).show()
        }

    }



    private fun setTabStrip() {

        //set the entries (titles and types)
        val tabStripEntries = presenter.getTabStripEntries()
        refreshTabs(tabStripEntries)
        //set onclick
        tab_strip.onTabStripSelectedIndexListener = object : NavigationTabStrip.OnTabStripSelectedIndexListener {
            override fun onStartTabSelected(title: String, index: Int) {}
            override fun onEndTabSelected(title: String?, type: ListStateFactory.ListStateType?, index: Int)
                    = presenter.onTabChanged(title!!, type!!)

        }
    }

    companion object { var mTTS: TextToSpeech? = null }

    override fun onDestroy() {
        if (mTTS != null) {
            mTTS?.stop()
            mTTS?.shutdown()
        }
        super.onDestroy()
    }
}
