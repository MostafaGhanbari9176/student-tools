package ir.pepotec.app.awesomeapp.view.chat

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import ir.pepotec.app.awesomeapp.model.chat.*
import ir.pepotec.app.awesomeapp.presenter.student.ChatPresenter
import ir.pepotec.app.awesomeapp.presenter.student.StudentProfilePresenter
import ir.pepotec.app.awesomeapp.view.uses.App
import java.io.File
import java.lang.Exception

class ServiceChat : Service() {

    private var lastId = -1
    private var firstId = -1

    interface ChatInterface {
        fun newMessage(data: ArrayList<ChatMessageData>) {}
        fun updateSeen(lastSeenId: Int) {}
        fun lastSeenData(message: String){}
    }

    var listener: ChatInterface? = null
    var chat_id: Int = -1
    var kind_id: String = ""

    override fun onCreate() {
        super.onCreate()
        getChatList()
    }

    private fun getChatList() {
        StudentProfilePresenter(object : StudentProfilePresenter.StudentProfileResult {
            override fun chatListRes(ok: Boolean, singleNum: Int, data: ArrayList<ChatListData>) {
                if (ok)
                    checkLocalChatList(data)
                processOnChatMessage()
            }
        }).getChatList()
    }

    private fun processOnChatMessage() {
        if (firstId > lastId) {
            getNewMessage("s")
            return
        }
        ChatListDb().getSingle(firstId)?.let {
            checkUnSuccessful(it.chat_id, it.kind_id)
        } ?: run {
           startTimer()
        }
    }

    private fun checkUnSuccessful(chat_id: Int, kind_id: String) {
        val data = ChatMessageDb(chat_id, kind_id).getUnsuccessful()
        if (data.size > 0) {
            ChatPresenter(object : ChatPresenter.ChatResult {
                override fun chatResponse(ok: Boolean, message: String) {
                    if (ok) {
                        firstId++
                        ChatMessageDb(chat_id, kind_id).deleteUnsuccessful()
                    }
                    processOnChatMessage()
                }
            }).sendMessage(chat_id, kind_id, data)
        } else {
            firstId++
            processOnChatMessage()
        }
    }

    private fun getNewMessage(kind_id: String) {
        val chatList = ChatListDb().getData(kind_id)
        if (chatList.isNullOrEmpty()) {
            if (kind_id == "s")
                getNewMessage("g")
            else
                getLastSeen()
            return
        }
        val reqData = ArrayList<ChatReq>()
        for (o in chatList) {
            val lastId = ChatMessageDb(o.chat_id, kind_id).getLastId()
            reqData.add(ChatReq(o.chat_id, kind_id, lastId))
        }
        ChatPresenter(object : ChatPresenter.ChatResult {
            override fun newData(ok: Boolean, message: String, data: ArrayList<ChatRes>) {
                if (ok && data.size > 0)
                    newMessageData(data)
                if (kind_id == "s")
                    getNewMessage("g")
                else
                    getLastSeen()
            }
        }).getNewMessage(reqData, kind_id)

    }

    private fun getLastSeen() {
        if(chat_id == -1 || kind_id != "s")
        {
            startTimer()
            return
        }
        StudentProfilePresenter(object:StudentProfilePresenter.StudentProfileResult{
            override fun result(ok: Boolean, message: String) {
                if(ok) {
                    try {
                        listener?.lastSeenData(message)
                    } catch (e: Exception) {
                    }
                }
                startTimer()
            }
        }).lastSeen(chat_id)
    }

    private fun newMessageData(data: ArrayList<ChatRes>) {
        for (o in data) {
            if (o.messageList.isNullOrEmpty())
                continue
            for (o2 in o.messageList)
                pathProssecing(o2)
            ChatMessageDb(o.chat_id, o.kind_id).saveData(o.messageList)
            if (chat_id == o.chat_id && kind_id == o.kind_id)
                listener?.newMessage(o.messageList)
            if (o.lastSeenId != -1) {
                ChatMessageDb(o.chat_id, o.kind_id).updateSeen(o.lastSeenId)
                listener?.updateSeen(o.lastSeenId)
            }


        }
    }

    private fun pathProssecing(data: ChatMessageData) {
        var path = ""
        if (data.path_id != 0 && data.its_my)
            path = PathDb().getPath(data.path_id)
        if (data.file_id != 0 && !File(path).exists()) {
            path = App.rootDir + "/${data.file_id}.jpg"
        }
        data.fPath = path
    }

    private fun checkLocalChatList(data: ArrayList<ChatListData>) {
        val localData = ChatListDb().getAllData()
        for (s in data) {
            var counter = 0
            while (counter < localData.size) {
                if (localData[counter].chat_id == s.chat_id && localData[counter].kind_id == s.kind_id) {
                    localData.remove(localData[counter])
                } else
                    counter++
            }
        }

        for (o in localData) {
            ChatMessageDb(o.chat_id, o.kind_id).removeTable()
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

    private fun startTimer()
    {
        Handler().postDelayed({
            getChatList()
        },5000)
    }

}