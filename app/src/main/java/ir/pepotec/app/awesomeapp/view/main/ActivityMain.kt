package ir.pepotec.app.awesomeapp.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.main.home.FragmentHome
import ir.pepotec.app.awesomeapp.view.main.news.FragmentNews
import ir.pepotec.app.awesomeapp.view.uses.App
import ir.pepotec.app.awesomeapp.view.uses.MyFragment

import kotlinx.android.synthetic.main.activity_main.*

class ActivityMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.instanse = this
        changeView(FragmentHome())

    }

    private fun changeView(f:MyFragment)
    {
        supportFragmentManager.beginTransaction().replace(R.id.ContentMain, f).commit()
    }

    override fun onResume() {
        super.onResume()
        App.instanse = this
        //App.hideActionButton()
    }
}
