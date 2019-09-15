package ir.pepotec.app.awesomeapp.presenter.student

import com.google.gson.Gson
import ir.pepotec.app.awesomeapp.model.ServerRes
import ir.pepotec.app.awesomeapp.model.chat.*
import ir.pepotec.app.awesomeapp.model.user.UserDb
import ir.pepotec.app.awesomeapp.view.uses.AF
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ChatPresenter(private val listener: ChatResult) {

    interface ChatResult {
        fun chatResponse(ok: Boolean, message: String) {}
        fun newData(ok: Boolean, message: String, data: ArrayList<ChatRes>) {}
    }

    fun sendFileMessage(message: ChatMessageData, chat_id: Int, kind_id: String) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        if (message.path_id == 0) {
            PathDb().addPath(message.fPath)
            val pathId = PathDb().getLastId()
            message.path_id = pathId
           // FileMessageDb().saveData(message)
        }
        val phoneBody = RequestBody.create(MultipartBody.FORM, phone)
        val messageBody = RequestBody.create(MultipartBody.FORM, message.m_text)
        val acBody = RequestBody.create(MultipartBody.FORM, ac)
        val kindIdBody = RequestBody.create(MultipartBody.FORM, kind_id)
        val userBody = RequestBody.create(MultipartBody.FORM, message.user_id.toString())
        val chatIdBody = RequestBody.create(MultipartBody.FORM, chat_id.toString())
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
        }).sendFileMessage(phoneBody, acBody, chatIdBody, kindIdBody, userBody, pathIdBody, messageBody, fileBody)

    }

    fun getNewMessage(reqData: ArrayList<ChatReq>, kind_id:String) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        val dataMap = ArrayList<String>()
        for (o in reqData)
            dataMap.add(Gson().toJson(o))
        Chat(object : Chat.ChatRes {
            override fun response(res: ServerRes?) {
                val data = ArrayList<ChatRes>()
                res?.let {
                    for (i in it.data.indices) {
                        data.add(Gson().fromJson(it.data[i], ChatRes::class.java))
                    }
                }
                listener.newData(
                    res?.code == ServerRes.ok,
                    res?.message ?: AF().serverMessage(res?.code ?: -1),
                    data
                )
            }
        }).getNewMessage(phone, ac, kind_id, dataMap)
    }

    /*fun getLastMessage(userId: Int) {
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
    }*/

    fun sandMessage(message: ChatMessageData, chat_id:Int, kind_id:String) {
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
        }).sendMessage(phone, ac, chat_id, kind_id, ArrayList<String>().apply { add(message.m_text) })
    }

    private fun saveUnsuccessful(message: ChatMessageData) {
        //ChatMessageDb(message.chat_id).newMessage(message)
    }

    fun sendMessage(chat_id:Int, kind_id:String, messageList: ArrayList<String>) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        Chat(object : Chat.ChatRes {
            override fun response(res: ServerRes?) {
                listener.chatResponse(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1))
            }
        }).sendMessage(phone, ac, chat_id, kind_id, messageList)
    }

    fun updateSeen(chat_id:Int, kind_id:String) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        Chat(object : Chat.ChatRes {})
            .updateSeen(phone, ac, chat_id, kind_id)
    }

}