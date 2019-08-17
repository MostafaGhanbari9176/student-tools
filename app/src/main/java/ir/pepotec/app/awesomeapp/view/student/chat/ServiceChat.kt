package ir.pepotec.app.awesomeapp.view.student.chat

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import ir.pepotec.app.awesomeapp.model.student.chat.ChatListData
import ir.pepotec.app.awesomeapp.model.student.chat.ChatListDb
import ir.pepotec.app.awesomeapp.model.student.chat.ChatMessageData
import ir.pepotec.app.awesomeapp.model.student.chat.ChatMessageDb
import ir.pepotec.app.awesomeapp.presenter.student.ChatPresenter
import ir.pepotec.app.awesomeapp.presenter.student.StudentProfilePresenter

class ServiceChat : Service() {

    private var lastId = -1
    private var firstId = -1

    interface ChatInterface {
        fun newMessage(data: ArrayList<ChatMessageData>) {}
        fun updateSeen(lastSeenId: Int) {}
    }

    var listener: ChatInterface? = null
    var user_id: Int = -1

    override fun onCreate() {
        super.onCreate()
        getChatList()
    }

    private fun getChatList() {
        StudentProfilePresenter(object : StudentProfilePresenter.StudentProfileResult {
            override fun chatListRes(ok: Boolean, data: ArrayList<ChatListData>) {
                if (ok)
                    checkLocalChatList(data)
                processOnChatMessage()
            }
        }).getChatList()
    }

    private fun processOnChatMessage() {
        if (firstId > lastId) {
            getChatList()
            return
        }
        ChatListDb().getSingle(firstId)?.let {
            checkUnSuccessful(it.user_id)
        } ?: run {
            getChatList()
        }
        firstId++
    }

    private fun checkUnSuccessful(userId: Int) {
        val data = ChatMessageDb(userId).getUnsuccessful()
        if (data.size > 0) {
            ChatPresenter(object : ChatPresenter.ChatResult {
                override fun chatResponse(ok: Boolean, message: String) {
                    if (ok) {
                        ChatMessageDb(userId).deleteUnsuccessful()
                        val data2 = ChatMessageDb(userId).getUnsuccessful()
                        getNewMessage(userId)
                    } else
                        processOnChatMessage()
                }
            }).sendMessage(userId, data)
        } else {
            getNewMessage(userId)
        }
    }

    private fun getNewMessage(userId: Int) {
        val lastMessageId = ChatMessageDb(userId).getLastId()
        ChatPresenter(object : ChatPresenter.ChatResult {
            override fun newData(ok: Boolean, message: String, data: ArrayList<ChatMessageData>, lastSeenId: Int) {
                if (ok && data.size > 0)
                    newMessageData(data, userId)
                if (lastSeenId != -1 && user_id == userId) {
                    listener?.updateSeen(lastSeenId)
                    ChatMessageDb(userId).updateSeen(lastSeenId)
                }
                processOnChatMessage()
            }
        }).apply {
            if (lastMessageId == -1)
                getLastMessage(userId)
            else
                getNewMessage(userId, lastMessageId)
        }
    }

    private fun newMessageData(data: ArrayList<ChatMessageData>, userId: Int) {
        for (o in data)
            o.its_my = o.user_id != userId
        ChatMessageDb(userId).saveData(data)
        if (this@ServiceChat.user_id == userId)
            listener?.newMessage(data)
    }

    private fun checkLocalChatList(data: ArrayList<ChatListData>) {
        val localData = ChatListDb().getData()
        localData.removeAll(data)
        for (o in localData) {
            ChatMessageDb(o.user_id).removeTable()
        }
        ChatListDb().apply {
            deleteData()
            saveData(data)
            lastId = lastId()
            firstId = firstId()
        }
    }

    private val binder = ChatBinder()

    inner class ChatBinder : Binder() {
        fun getService(): ServiceChat = this@ServiceChat
    }

    override fun onBind(intent: Intent?): IBinder? = binder

}