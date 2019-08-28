package ir.pepotec.app.awesomeapp.view.student.profile.activityProfile

import android.os.Bundle
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.student.profile.activityProfile.friendList.FragmentFriendList
import ir.pepotec.app.awesomeapp.view.uses.App
import ir.pepotec.app.awesomeapp.view.uses.MyActivity

class ActivityProfile : MyActivity() {
    var user_id = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_common)
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