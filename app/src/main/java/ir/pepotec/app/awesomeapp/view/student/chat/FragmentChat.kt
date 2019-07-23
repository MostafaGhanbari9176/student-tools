package ir.pepotec.app.awesomeapp.view.student.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.student.ActivityStudent
import ir.pepotec.app.awesomeapp.view.student.chat.chatContact.FragmentChatContact
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_chat.*
import java.util.*

class FragmentChat:MyFragment() {

    val backHistory = Stack<MyFragment>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val f = FragmentChatContact()
        f.parent = this
        changeView(f)
    }

    fun changeView(f:MyFragment)
    {
        backHistory.push(f)
        (ctx as ActivityStudent).supportFragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.ContentChat, f).commit()
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