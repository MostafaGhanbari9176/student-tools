package ir.pepotec.app.awesomeapp.model.invite

import ir.pepotec.app.awesomeapp.model.ApiClient
import ir.pepotec.app.awesomeapp.model.ServerRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Invite(private val listener:Res): Callback<ServerRes> {

    companion object{
        const val baseUrl = "chat/invite/index.php/"
    }

    interface Res{
        fun result(res:ServerRes?){}
    }

   private val api:InviteApi = ApiClient.getClient().create(InviteApi::class.java)

    fun add(phone:String, apiCode:String, groupId:Int, userId:Int)
    {
        api.add(phone, apiCode, userId, groupId).apply { enqueue(this@Invite) }
    }

    fun accept(phone:String, apiCode:String, inviteIt:Int)
    {
        api.accept(phone, apiCode, inviteIt).apply { enqueue(this@Invite) }
    }

    override fun onFailure(call: Call<ServerRes>, t: Throwable) {
    listener.result(null)
    }

    override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
        listener.result(response.body())
    }
}