package ir.pepotec.app.awesomeapp.view.student.ability.activityAbility

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.uses.App
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import java.util.*

class ActivityAbility : AppCompatActivity() {

    val backHistory = Stack<MyFragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ability)
        App.instanse = this
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        changeView(if (intent?.extras?.getBoolean("add") == true) FragmentAddAbility() else FragmentShowAbility())
    }

    fun changeView(f: MyFragment) {
        backHistory.add(f)
        supportFragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.ContentAbility, f).commit()
    }

    override fun onBackPressed() {
        if (backHistory.size <= 1) {
            super.onBackPressed()
            this.finish()
        }
        else {
            backHistory.pop()
            changeView(backHistory.pop())
        }
    }

}