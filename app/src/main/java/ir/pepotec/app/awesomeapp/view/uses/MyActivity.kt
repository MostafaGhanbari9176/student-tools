package ir.pepotec.app.awesomeapp.view.uses

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import ir.pepotec.app.awesomeapp.R
import java.util.*

abstract class MyActivity(@IdRes private val content: Int = R.id.ContentCommon) : AppCompatActivity() {

    val backHistory = Stack<MyFragment>()
    open fun changeView(f: MyFragment) {
        backHistory.add(f)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(content, f).commit()
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