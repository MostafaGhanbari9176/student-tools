package ir.pepotec.app.awesomeapp.view.uses

import android.app.Application
import android.content.Context
import android.content.Intent
import android.widget.Toast
import ir.pepotec.app.awesomeapp.view.chat.ServiceChat
import java.io.File

class App : Application() {

    companion object {
        lateinit var instanse: Context
        val rootDir = android.os.Environment.getExternalStorageDirectory().absolutePath+"/AwesomeApp"
        fun apiCodeError() {
            Toast.makeText(instanse, "ApiCodeError", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate() {
        super.onCreate()
        instanse = this
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