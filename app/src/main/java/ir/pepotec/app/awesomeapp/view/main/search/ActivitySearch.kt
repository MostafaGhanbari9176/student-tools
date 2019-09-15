package ir.pepotec.app.awesomeapp.view.main.search

import android.os.Bundle
import androidx.core.content.ContextCompat
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.main.search.searchAbility.FragmentAbilitySearch
import ir.pepotec.app.awesomeapp.view.uses.App
import ir.pepotec.app.awesomeapp.view.uses.MyActivity

class ActivitySearch : MyActivity() {

    var groupId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_common)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        App.instanse = this
        groupId = intent?.extras?.getInt("groupId") ?: -1
        changeView(
            when (intent?.extras?.getString("flag") ?: "student") {
                "ability" -> FragmentAbilitySearch()
                else -> FragmentStudentSearch().apply { groupId = this@ActivitySearch.groupId }
            }
        )

    }

    override fun onResume() {
        super.onResume()
        App.instanse = this
    }

}