package ir.pepotec.app.awesomeapp.view.main

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity;
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.main.home.FragmentHome
import ir.pepotec.app.awesomeapp.view.uses.App
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.activity_main.*

class ActivityMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.instanse = this
        setSupportActionBar(barMain)
        changeView(FragmentHome())

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true

    }

    private fun changeView(f:MyFragment)
    {
        supportFragmentManager.beginTransaction().replace(R.id.ContentMain, f).commit()
    }

    override fun onResume() {
        super.onResume()
        App.instanse = this
        App.hideActionButton()
    }
}
