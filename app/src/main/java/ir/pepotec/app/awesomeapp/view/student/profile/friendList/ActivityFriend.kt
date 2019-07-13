package ir.pepotec.app.awesomeapp.view.student.profile.friendList

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.uses.App
import ir.pepotec.app.awesomeapp.view.uses.MyFragment

class ActivityFriend:AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend)
        App.instanse = this
        changeView(FragmentFriendList())
    }

    fun changeView(f:MyFragment)
    {
        supportFragmentManager.beginTransaction().replace(R.id.ContentFriend, f).commit()
    }

    override fun onResume() {
        super.onResume()
        App.instanse = this
    }

}