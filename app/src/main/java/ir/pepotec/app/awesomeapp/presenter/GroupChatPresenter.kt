package ir.pepotec.app.awesomeapp.presenter

import com.google.gson.Gson
import ir.pepotec.app.awesomeapp.model.ServerRes
import ir.pepotec.app.awesomeapp.model.chat.ChatListData
import ir.pepotec.app.awesomeapp.model.groupChat.GroupChat
import ir.pepotec.app.awesomeapp.model.groupChat.GroupChatData
import ir.pepotec.app.awesomeapp.model.student.profile.StudentProfileData
import ir.pepotec.app.awesomeapp.model.user.UserDb
import ir.pepotec.app.awesomeapp.view.uses.AF
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class GroupChatPresenter(private val listener: Res) {

    interface Res {
        fun result(ok: Boolean, message: String, data: GroupChatData?) {}
        fun memberList(ok: Boolean, message: String, data: ArrayList<StudentProfileData>?) {}
    }

    private val phone = UserDb().getUserPhone()
    private val ac = UserDb().getUserApiCode()

    fun add(name: String, info: String, joinMode: Int, inviteMode: Int) {
        GroupChat(object : GroupChat.Res {
            override fun result(res: ServerRes?) {
                listener.result(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1), null)
            }
        }).add(phone, ac, name, info, joinMode, inviteMode)
    }

    fun getData(groupId: Int) {
        GroupChat(object : GroupChat.Res {
            override fun result(res: ServerRes?) {
                var data: GroupChatData? = null
                res?.let {
                    data = Gson().fromJson(it.data[0], GroupChatData::class.java)
                }
                listener.result(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1), data)
            }
        }).getData(phone, ac, groupId)
    }

    fun memberList(groupId: Int) {
        GroupChat(object : GroupChat.Res {
            override fun result(res: ServerRes?) {
                val data = ArrayList<StudentProfileData>()

                res?.let {
                    for (i in it.data.indices)
                        data.add(Gson().fromJson(it.data[i], StudentProfileData::class.java))
                }
                listener.memberList(
                    res?.code == ServerRes.ok,
                    res?.message ?: AF().serverMessage(res?.code ?: -1),
                    data
                )
            }
        }).memberList(phone, ac, groupId)
    }

    fun left(groupId: Int) {
        GroupChat(object : GroupChat.Res {
            override fun result(res: ServerRes?) {
                listener.result(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1), null)
            }
        }).left(phone, ac, groupId)
    }

    fun removeMember(groupId: Int, user_id: Int) {
        GroupChat(object : GroupChat.Res {
            override fun result(res: ServerRes?) {
                listener.result(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1), null)
            }
        }).removeMember(phone, ac, groupId, user_id)
    }

    fun uploadImage(groupId: Int, f: File) {
        val pBody = RequestBody.create(MultipartBody.FORM, phone)
        val acBody = RequestBody.create(MultipartBody.FORM, ac)
        val gIdBody = RequestBody.create(MultipartBody.FORM, groupId.toString())

        val fileBody = RequestBody.create(MediaType.parse(".jpg"), f)
        val multiData = MultipartBody.Part.createFormData("img", "img", fileBody)

        GroupChat(object : GroupChat.Res {
            override fun result(res: ServerRes?) {
                listener.result(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1), null)
            }
        }).uploadImage(pBody, acBody, gIdBody, multiData)

    }

}