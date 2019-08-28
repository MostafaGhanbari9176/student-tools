package ir.pepotec.app.awesomeapp.model.blog

import ir.pepotec.app.awesomeapp.model.ApiClient
import ir.pepotec.app.awesomeapp.model.ServerRes
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Blog(private val listener:BlogRes): Callback<ServerRes> {

    companion object{
        const val baseUrl = "blog/index.php/"
    }

    interface BlogRes{
        fun response(res:ServerRes?){}
        fun imageDate(data:ByteArray?){}
    }

    private val api:BlogApi = ApiClient.getClient().create(BlogApi::class.java)

    fun add(phone:String, ac:String, message:String)
    {
        api.add(phone, ac, message).apply { enqueue(this@Blog) }
    }

    fun addWithImg(phone:RequestBody, ac:RequestBody, message:RequestBody, file:MultipartBody.Part)
    {
        api.addWithImg(phone, ac, message, file).apply { enqueue(this@Blog) }
    }

    fun getAllData(phone:String, ac:String, num:Int, step:Int){
        api.getAll(phone, ac, num, step).apply { enqueue(this@Blog) }
    }

    fun getPerLike(phone:String, ac:String, num:Int, step:Int){
        api.getPerLike(phone, ac, num, step).apply { enqueue(this@Blog) }
    }

    fun changeLike(phone:String, ac:String, mId:Int)
    {
        api.changLike(phone, ac, mId).apply { enqueue(this@Blog) }
    }

    fun report(phone:String, ac:String, mId:Int, status:Int)
    {
        api.report(phone, ac, mId, status).apply { enqueue(this@Blog) }
    }

    fun getImg(phone:String, ac:String, imgId:Int){
        val req = api.getImg(phone, ac, imgId)
        req.enqueue(object:Callback<ResponseBody>{
            override fun onFailure(p0: Call<ResponseBody>, p1: Throwable) {
                listener.imageDate(null)
            }

            override fun onResponse(p0: Call<ResponseBody>, p1: Response<ResponseBody>) {
                listener.imageDate(p1.body()?.bytes())
            }
        })
    }

    override fun onFailure(p0: Call<ServerRes>, p1: Throwable) {
        listener.response(null)
    }

    override fun onResponse(p0: Call<ServerRes>, p1: Response<ServerRes>) {
        listener.response(p1.body())
    }
}