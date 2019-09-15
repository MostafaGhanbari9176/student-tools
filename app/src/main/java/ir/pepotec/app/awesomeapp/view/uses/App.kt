package ir.pepotec.app.awesomeapp.view.uses

import android.app.Activity
import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.renderscript.ScriptC
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ir.pepotec.app.awesomeapp.model.chat.ChatListDb
import ir.pepotec.app.awesomeapp.model.chat.ChatMessageDb
import ir.pepotec.app.awesomeapp.view.chat.ServiceChat
import java.io.File

class App : Application() {

    companion object {
        lateinit var instanse: Context
        val rootDir = android.os.Environment.getExternalStorageDirectory().absolutePath+"/AwesomeApp"
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

        fun apiCodeError() {
            Toast.makeText(instanse, "ApiCodeError", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate() {
        super.onCreate()
        instanse = this
        //ChatListDb().deleteDb()
        //ChatMessageDb(1).deleteDb()
        checkForAppDir()
        try {
            val intent = Intent(this, ServiceChat::class.java)
            startService(intent)
        } catch (ignored: Exception) {
        }
    }

    private fun checkForAppDir() {
        val rootPath = android.os.Environment.getExternalStorageDirectory().absolutePath
        val dir = File(rootPath + "/AwesomeApp")
        if (!dir.exists())
            dir.mkdir()
    }
}