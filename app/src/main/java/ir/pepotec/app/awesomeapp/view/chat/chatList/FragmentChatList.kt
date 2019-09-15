package ir.pepotec.app.awesomeapp.view.chat.chatList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.pepotec.app.awesomeapp.model.chat.ChatListDb
import ir.pepotec.app.awesomeapp.view.chat.ActivityChat
import ir.pepotec.app.awesomeapp.view.chat.messageList.FragmentMessageList
import ir.pepotec.app.awesomeapp.view.uses.MyFragment

class FragmentChatList:MyFragment() {

    var kind_id = "s"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return RecyclerView(ctx).apply {
            layoutDirection = View.LAYOUT_DIRECTION_RTL
            layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.MATCH_PARENT)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (view as RecyclerView).apply {
            val data = ChatListDb().getData(kind_id)
            adapter = AdapterChatList(data){
                (ctx as ActivityChat).changeView(FragmentMessageList().apply { chat_id =  data[it].chat_id; kind_id = data[it].kind_id})
            }
            layoutManager = LinearLayoutManager(ctx)
        }
    }
}