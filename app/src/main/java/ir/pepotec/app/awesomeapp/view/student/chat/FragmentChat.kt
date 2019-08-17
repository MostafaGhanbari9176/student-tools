package ir.pepotec.app.awesomeapp.view.student.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.student.ActivityStudent
import ir.pepotec.app.awesomeapp.view.student.chat.chatList.FragmentChatList
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import java.util.*

class FragmentChat:MyFragment() {

    val backHistory = Stack<MyFragment>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.content_common, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val f = FragmentChatList()
        f.parent = this
        changeView(f)
    }

    fun changeView(f:MyFragment)
    {
        backHistory.push(f)
        (ctx as ActivityStudent).supportFragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.ContentCommon, f).commit()
    }

    public fun onBackPresed():Boolean
    {
        if(backHistory.size <= 1)
            return true
        else
        {
            backHistory.pop()
            changeView(backHistory.pop())
            return false
        }
    }
}