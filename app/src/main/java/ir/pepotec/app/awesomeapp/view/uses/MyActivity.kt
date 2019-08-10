package ir.pepotec.app.awesomeapp.view.uses

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import java.util.*

abstract class MyActivity(@IdRes private val content:Int? = null) : AppCompatActivity() {

    val backHistory = Stack<MyFragment>()
    fun changeView(f: MyFragment) {
        content?.let {
            backHistory.add(f)
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(it, f).commit()
        }
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
}