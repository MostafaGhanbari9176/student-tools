package ir.pepotec.app.awesomeapp.view.student.profile.activityProfile

import android.os.Bundle
import androidx.core.content.ContextCompat
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.student.profile.activityProfile.friendList.FragmentFriendList
import ir.pepotec.app.awesomeapp.view.uses.App
import ir.pepotec.app.awesomeapp.view.uses.MyActivity

class ActivityProfile : MyActivity() {
    var user_id = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_common)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        App.instanse = this
        user_id = intent?.extras?.getInt("userId") ?: -1
        changeView(if (user_id == -1) FragmentFriendList() else FragmentOtherProfile().apply {
            this@apply.user_id = this@ActivityProfile.user_id
        })
    }

    override fun onResume() {
        super.onResume()
        App.instanse = this
    }

}