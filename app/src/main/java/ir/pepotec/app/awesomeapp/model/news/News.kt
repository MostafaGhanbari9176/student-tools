package ir.pepotec.app.awesomeapp.model.news

import ir.pepotec.app.awesomeapp.model.ApiClient
import ir.pepotec.app.awesomeapp.model.ServerRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class News(private val listener: Res) : Callback<ServerRes> {

    interface Res {
        fun result(res: ServerRes?){}
    }

    companion object {
        const val baseUrl = "news/index.php/"
    }

    private val api: NewsApi = ApiClient.getClient().create(NewsApi::class.java)

     fun getFirstData(phone: String, apiCode: String) {
        api.getFirstData(phone, apiCode).apply { enqueue(this@News) }
    }

     fun get(phone: String, apiCode: String, gId: Int) {
        api.get(phone, apiCode, gId).apply { enqueue(this@News) }
    }

     fun getListData(phone: String, apiCode: String, gId: Int, num: Int, step: Int) {
        api.getListData(phone, apiCode, gId, num, step).apply { enqueue(this@News) }
    }

     fun getSpecial(phone: String, apiCode: String) {
        api.getSpecial(phone, apiCode).apply { enqueue(this@News) }
    }

    override fun onFailure(call: Call<ServerRes>, t: Throwable) {
        listener.result(null)
    }

    override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
        listener.result(response.body())
    }
}
