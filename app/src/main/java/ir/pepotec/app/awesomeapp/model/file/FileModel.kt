package ir.pepotec.app.awesomeapp.model.file

import ir.pepotec.app.awesomeapp.model.ApiClient
import ir.pepotec.app.awesomeapp.model.ServerRes
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FileModel(private val listener: FileRes) {

    companion object {
        const val baseUrl = "file/index.php/"
    }

    interface FileRes {
        fun fileData(res: ByteArray?){}
        fun response(res:ServerRes?){}
    }

    fun downloadFile(phone:String, ac:String, fileId:Int)
    {
        val api = ApiClient.getClient().create(FileApi::class.java)
        val req = api.downloadFile(fileId, phone, ac)

        req.enqueue(object: Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                listener.fileData(null)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                listener.fileData(response.body()?.bytes())
            }
        })
    }

    fun getType(phone:String, ac:String, fileId:Int)
    {
        val api = ApiClient.getClient().create(FileApi::class.java)
        val req = api.getType(fileId, phone, ac)

        req.enqueue(object: Callback<ServerRes>{
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.response(null)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.response(response.body())
            }
        })
    }

}