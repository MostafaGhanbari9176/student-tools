package ir.pepotec.app.awesomeapp.view.student.chat.messageList

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.student.chat.ServiceChat
import ir.pepotec.app.awesomeapp.view.uses.App
import ir.pepotec.app.awesomeapp.view.uses.MyActivity
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import org.jetbrains.anko.toast

class ActivityMessageList : MyActivity() {

    var user_id = -1
    var fragmentMessageList: FragmentMessageList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_common)
        App.instanse = this@ActivityMessageList
        user_id = intent?.extras?.getInt("user_id") ?: -1
        changeView(FragmentMessageList().apply { user_id = this@ActivityMessageList.user_id })
    }

    override fun changeView(f: MyFragment) {
        if (f is FragmentMessageList) {
            fragmentMessageList = f
        }
        super.changeView(f)
    }

    override fun onResume() {
        super.onResume()
        App.instanse = this
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            fragmentMessageList?.fileChoosed(data?.getStringExtra("path") ?: "")

        }
    }
}