package ir.pepotec.app.awesomeapp.view.uses

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import ir.pepotec.app.awesomeapp.R
import java.util.*

abstract class MyActivity(@IdRes private val content: Int = R.id.ContentCommon) : AppCompatActivity() {

    val backHistory = Stack<MyFragment>()
    open fun changeView(f: MyFragment) {
        if (backHistory.size > 0 && f::class == backHistory.peek()::class)
            return
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(content, addFragment(f)).commit()
    }

    private fun addFragment(f: MyFragment): MyFragment {
        val i = backHistory.search(f)
        return if (i != -1)
            backHistory[i]
        else {
            backHistory.add(f)
            f
        }

    }

    override fun onBackPressed() {
        if (backHistory.size <= 1) {
            super.onBackPressed()
            this.finish()
        } else {
            backHistory.pop()
            changeView(backHistory.pop())
        }
    }
}