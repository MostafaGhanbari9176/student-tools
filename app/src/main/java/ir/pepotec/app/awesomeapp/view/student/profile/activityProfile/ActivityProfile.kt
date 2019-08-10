package ir.pepotec.app.awesomeapp.view.student.profile.activityProfile

import android.os.Bundle
import androidx.core.content.ContextCompat
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.student.profile.activityProfile.friendList.FragmentFriendList
import ir.pepotec.app.awesomeapp.view.uses.App
import ir.pepotec.app.awesomeapp.view.uses.MyActivity
import kotlinx.android.synthetic.main.activity_profile.*

class ActivityProfile : MyActivity(R.id.ContentFriend) {
    var userId = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        App.instanse = this
        userId = intent?.extras?.getInt("userId") ?: -1
        fabActivityProfile.apply {
            if (userId != -1)
                hide()
            else
                show()
        }
        changeView(if (userId == -1) FragmentFriendList() else FragmentOtherProfile().apply {
            this@apply.userId = this@ActivityProfile.userId
        })
    }

    override fun onResume() {
        super.onResume()
        App.instanse = this
    }

}