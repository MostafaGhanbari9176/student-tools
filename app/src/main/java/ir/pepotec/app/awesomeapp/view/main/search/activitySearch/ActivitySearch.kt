package ir.pepotec.app.awesomeapp.view.main.search.activitySearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.uses.App
import ir.pepotec.app.awesomeapp.view.uses.MyActivity
import ir.pepotec.app.awesomeapp.view.uses.MyFragment

class ActivitySearch : MyActivity(R.id.ContentSearch) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        App.instanse = this
        val flag = intent?.extras?.getString("flag") ?: "student"
        when (flag)
        {
            "student" -> changeView(FragmentStudentSearch())
        }

    }

    override fun onResume() {
        super.onResume()
        App.instanse = this
    }

}