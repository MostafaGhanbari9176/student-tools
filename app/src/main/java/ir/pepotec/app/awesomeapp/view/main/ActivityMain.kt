package ir.pepotec.app.awesomeapp.view.main

import android.os.Bundle
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.main.search.FragmentSearchList
import ir.pepotec.app.awesomeapp.view.uses.App
import ir.pepotec.app.awesomeapp.view.uses.MyActivity
import kotlinx.android.synthetic.main.activity_main.*

class ActivityMain : MyActivity(R.id.ContentMain) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.instanse = this
        changeView(FragmentHomeList())
        setUpBottomNavListener()

    }

    private fun setUpBottomNavListener() {
        bnvMain.setOnNavigationItemSelectedListener {
            when(it.itemId)
            {
                R.id.mainMenuHome -> changeView(FragmentHomeList())
                R.id.mainMenuSearch -> changeView(FragmentSearchList())
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        App.instanse = this
       // App.hideActionButton()
    }
}
