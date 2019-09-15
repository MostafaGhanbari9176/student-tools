package ir.pepotec.app.awesomeapp.model.groupChat

import ir.pepotec.app.awesomeapp.model.ApiClient
import ir.pepotec.app.awesomeapp.model.ServerRes
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class GroupChat(private val listener:Res): Callback<ServerRes> {

    interface Res
    {
        fun result(res:ServerRes?){}
    }

    companion object
    {
        const val baseUrl = "chat/groupChat/index.php/"
    }

    private val api:GroupChatApi = ApiClient.getClient().create(GroupChatApi::class.java)

    fun add(phone:String, apiCode:String, name:String, info:String, joinMode:Int, inviteMode:Int)
    {
     api.add(phone, apiCode, name, info, joinMode, inviteMode) .apply { enqueue(this@GroupChat) }
    }

    fun getData(phone:String, apiCode:String, groupId:Int)
    {
        api.getData(phone, apiCode, groupId) .apply { enqueue(this@GroupChat) }
    }

    fun memberList(phone:String, apiCode:String, groupId:Int)
    {
        api.memberList(phone, apiCode, groupId) .apply { enqueue(this@GroupChat) }
    }

    fun left(phone:String, apiCode:String, groupId:Int)
    {
        api.left(phone, apiCode, groupId) .apply { enqueue(this@GroupChat) }
    }

    fun removeMember(phone:String, apiCode:String, groupId:Int, user_id:Int)
    {
        api.removeMember(phone, apiCode, groupId, user_id) .apply { enqueue(this@GroupChat) }
    }

    fun uploadImage(phone:RequestBody, apiCode:RequestBody, groupId:RequestBody, f: MultipartBody.Part)
    {
        api.uploadImage(phone, apiCode, groupId, f).apply { enqueue(this@GroupChat) }
    }

    override fun onFailure(call: Call<ServerRes>, t: Throwable) {
        listener.result(null)
    }

    override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
        listener.result(response.body())
    }

}