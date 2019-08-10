package ir.pepotec.app.awesomeapp.view.uses

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class App : Application() {

    companion object {
        lateinit var instanse: Context
        fun hideActionButton() {
            val context: AppCompatActivity = instanse as AppCompatActivity
            val flags = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

            context.window.decorView.systemUiVisibility = flags
            val decorView = context.window.decorView
            decorView.setOnSystemUiVisibilityChangeListener { visibility ->
                if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                    decorView.systemUiVisibility = flags
                }

            }
        }
        fun apiCodeError()
        {
            Toast.makeText(instanse,"ApiCodeError", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate() {
        super.onCreate()
        instanse = this
    }
}