package ir.pepotec.app.awesomeapp.model.chat
import ir.pepotec.app.awesomeapp.model.ApiClient
import ir.pepotec.app.awesomeapp.model.ServerRes
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Chat(private val listener: ChatRes):Callback<ServerRes> {
    companion object{
        const val baseUrl = "chat/index.php/"
    }

    private val api: ChatApi = ApiClient.getClient().create(ChatApi::class.java)

    interface ChatRes{
        fun response(res:ServerRes?){}
    }

    fun getOldMessage(phone:String, ac:String, userId:Int, lastId:Int)
    {
      //  api.getOldMessage(phone, ac, userId, lastId).apply { enqueue(this@Chat) }
    }

    fun getNewMessage(phone:String, ac:String, kind_id:String, reqData:ArrayList<String>)
    {
        api.getNewMessage(phone, ac, kind_id, reqData).apply { enqueue(this@Chat) }
    }

    fun getLastMessage(phone:String, ac:String, userId:Int)
    {
        //api.getLastMessage(phone, ac, userId).apply { enqueue(this@Chat) }
    }

    fun sendMessage(phone:String, ac:String, chat_id:Int, kind_id:String, messageList:ArrayList<String>)
    {
        api.sendMessage(phone, ac, chat_id, kind_id, messageList).apply { enqueue(this@Chat) }
    }

    fun sendFileMessage(phone:RequestBody, ac:RequestBody, chat_id:RequestBody, kind_id:RequestBody,userId:RequestBody, pathId:RequestBody, message:RequestBody, file:MultipartBody.Part)
    {
        api.sendFileMessage(phone, ac, chat_id, kind_id, message, userId, pathId, file).apply { enqueue(this@Chat) }
    }

    fun updateSeen(phone:String, ac:String, chat_id:Int, kind_id:String)
    {
        api.updateSeen(phone, ac, chat_id, kind_id).apply { enqueue(this@Chat) }
    }

    override fun onFailure(call: Call<ServerRes>, t: Throwable) {
        listener.response(null)
    }

    override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
        listener.response(response.body())
    }

}