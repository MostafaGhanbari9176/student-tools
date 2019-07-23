package ir.pepotec.app.awesomeapp.view.account

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.uses.App
import ir.pepotec.app.awesomeapp.view.uses.DialogProgress
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import java.util.*

class ActivityAccount : AppCompatActivity() {

    private val backHistory = Stack<MyFragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        App.instanse = this
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        changeView(FragmentLogSign())
    }

    fun changeView(f: MyFragment) {
        backHistory.add(f)
        supportFragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.ContentAccount, f).commit()
    }

    override fun onBackPressed() {
        if (backHistory.size <= 1) {
            super.onBackPressed()
            this.finish()
        } else {
            backHistory.pop()
            val f = backHistory.pop()
            changeView(f)
        }
    }

    override fun onResume() {
        super.onResume()
        App.instanse = this
    }
}