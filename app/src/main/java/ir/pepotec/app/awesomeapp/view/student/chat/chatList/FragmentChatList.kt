package ir.pepotec.app.awesomeapp.view.student.chat.chatList

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.student.chat.ChatListDb
import ir.pepotec.app.awesomeapp.view.student.chat.FragmentChat
import ir.pepotec.app.awesomeapp.view.student.chat.messageList.ActivityMessageList
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_chat_contact.*

class FragmentChatList:MyFragment() {

    lateinit var parent:FragmentChat

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val data = ChatListDb().getData()
        RVChatContact.layoutManager = LinearLayoutManager(ctx)
        RVChatContact.adapter = AdapterChatList(data){
            startActivity(Intent(ctx, ActivityMessageList::class.java).apply { putExtra("user_id", it) })
        }
    }
}