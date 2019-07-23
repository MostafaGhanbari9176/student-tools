package ir.pepotec.app.awesomeapp.view.student.chat.chatContact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.student.chat.FragmentChat
import ir.pepotec.app.awesomeapp.view.student.chat.chatList.FragmentChatList
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_chat_contact.*

class FragmentChatContact:MyFragment() {

    lateinit var parent:FragmentChat

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        RVChatContact.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
        RVChatContact.adapter = AdapterChatContact(){
            parent.changeView(FragmentChatList())
        }
    }
}