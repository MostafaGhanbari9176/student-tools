package ir.pepotec.app.awesomeapp.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.student.profile.StudentProfileDb
import ir.pepotec.app.awesomeapp.model.user.UserDb
import ir.pepotec.app.awesomeapp.view.launcher.ActivityLauncher
import ir.pepotec.app.awesomeapp.view.main.news.FragmentNews
import ir.pepotec.app.awesomeapp.view.main.search.FragmentSearchList
import ir.pepotec.app.awesomeapp.view.uses.App
import ir.pepotec.app.awesomeapp.view.uses.MyActivity
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.activity_main.*

class ActivityMain : MyActivity(R.id.ContentMain) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.instanse = this
        setSupportActionBar(tlbMain)
        supportActionBar?.title = ""
        changeView(FragmentHomeList())
        setUpBottomNavListener()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.menuMainLogOut -> logOut()
            R.id.menuMainAboutUs -> DialogAboutUs().show()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun logOut() {
        StudentProfileDb().removeData()
        UserDb().removeData()
        startActivity(Intent(this, ActivityLauncher::class.java))
        this.finish()
    }

    private fun setUpBottomNavListener() {
        bnvMain.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.mainMenuHome -> changeView(FragmentHomeList())
                R.id.mainMenuSearch -> changeView(FragmentSearchList())
                R.id.mainMenuNews -> changeView(FragmentNews())
            }
            true
        }
    }

    override fun changeView(f: MyFragment) {
        tlbMain.visibility =
            if (f is FragmentHomeList || f is FragmentSearchList)
                View.VISIBLE
            else
                View.GONE
        super.changeView(f)
    }

    override fun onResume() {
        super.onResume()
        App.instanse = this
    }
}
