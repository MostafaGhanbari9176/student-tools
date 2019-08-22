package ir.pepotec.app.awesomeapp.presenter.student

import com.google.gson.Gson
import ir.pepotec.app.awesomeapp.model.ServerRes
import ir.pepotec.app.awesomeapp.model.student.chat.*
import ir.pepotec.app.awesomeapp.model.user.UserDb
import ir.pepotec.app.awesomeapp.view.uses.AF
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ChatPresenter(private val listener: ChatResult) {

    interface ChatResult {
        fun chatResponse(ok: Boolean, message: String) {}
        fun newData(ok: Boolean, message: String, data: ArrayList<ChatMessageData>, lastSeenId: Int) {}
    }

    fun sendFileMessage(message: ChatMessageData) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        if (message.path_id == 0) {
            PathDb().addPath(message.fPath)
            val pathId = PathDb().getLastId()
            message.path_id = pathId
            FileMessageDb().saveData(message)
        }
        val phoneBody = RequestBody.create(MultipartBody.FORM, phone)
        val messageBody = RequestBody.create(MultipartBody.FORM, message.m_text)
        val acBody = RequestBody.create(MultipartBody.FORM, ac)
        val userBody = RequestBody.create(MultipartBody.FORM, message.user_id.toString())
        val pathIdBody = RequestBody.create(MultipartBody.FORM, message.path_id.toString())
        val f = File(message.fPath)

        val requestBody = RequestBody.create(MediaType.parse(f.extension), f)
        val fileBody: MultipartBody.Part = MultipartBody.Part.createFormData("mostafa", f.name, requestBody)

        Chat(object : Chat.ChatRes {
            override fun response(res: ServerRes?) {
                if (res?.code == ServerRes.ok)
                    FileMessageDb().deleteData(message.m_id)
                  listener.chatResponse(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1))
            }
        }).sendFileMessage(phoneBody, acBody, userBody, pathIdBody, messageBody, fileBody)

    }

    fun getNewMessage(userId: Int, lastId: Int) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        Chat(object : Chat.ChatRes {
            override fun response(res: ServerRes?) {
                val data = ArrayList<ChatMessageData>()
                var lastSeenId = -1
                res?.let {
                    for (i in it.data.indices) {
                        if (i == 0)
                            lastSeenId = it.data[0].toInt()
                        else {
                            data.add(Gson().fromJson(it.data[i], ChatMessageData::class.java))
                            data[i-1].fPath = pathProssecing(data[i-1], userId)
                        }
                    }
                }
                listener.newData(
                    res?.code == ServerRes.ok,
                    res?.message ?: AF().serverMessage(res?.code ?: -1),
                    data,
                    lastSeenId
                )
            }
        }).getNewMessage(phone, ac, userId, lastId)
    }

    fun getLastMessage(userId: Int) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        Chat(object : Chat.ChatRes {
            override fun response(res: ServerRes?) {
                val data = ArrayList<ChatMessageData>()
                res?.let {
                    for (i in it.data.indices) {
                        data.add(Gson().fromJson(it.data[i], ChatMessageData::class.java))
                        data[i].fPath = pathProssecing(data[i], userId)
                    }
                }
                listener.newData(
                    res?.code == ServerRes.ok,
                    res?.message ?: AF().serverMessage(res?.code ?: -1),
                    data,
                    -1
                )
            }
        }).getLastMessage(phone, ac, userId)
    }

    fun sandMessage(message: ChatMessageData) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        Chat(object : Chat.ChatRes {
            override fun response(res: ServerRes?) {
                res?.message?.let {
                    listener.chatResponse(res.code == ServerRes.ok, res.message)
                }
                if (res?.code != ServerRes.ok)
                    saveUnsuccessful(message)

            }
        }).sendMessage(phone, ac, message.user_id, ArrayList<String>().apply { add(message.m_text) })
    }

    private fun saveUnsuccessful(message: ChatMessageData) {
        ChatMessageDb(message.user_id).newMessage(message)
    }

    fun sendMessage(userId: Int, messageList: ArrayList<String>) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        Chat(object : Chat.ChatRes {
            override fun response(res: ServerRes?) {
                listener.chatResponse(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1))
            }
        }).sendMessage(phone, ac, userId, messageList)
    }

    fun updateSeen(otherId: Int) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        Chat(object : Chat.ChatRes {}).updateSeen(phone, ac, otherId)
    }

    private fun pathProssecing(data: ChatMessageData, user_id:Int): String {
        var path = ""
        if (data.path_id != 0 && data.user_id != user_id)
            path = PathDb().getPath(data.path_id)
        if (!File(path).exists() && data.file_id != 0 )
            path = "${data.file_id}"
        return path
    }

}