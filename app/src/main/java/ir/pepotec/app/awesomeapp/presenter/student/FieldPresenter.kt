package ir.pepotec.app.awesomeapp.presenter.student

import com.google.gson.Gson
import ir.pepotec.app.awesomeapp.model.ApiClient
import ir.pepotec.app.awesomeapp.model.ServerRes
import ir.pepotec.app.awesomeapp.model.user.UserDb
import ir.pepotec.app.awesomeapp.view.uses.AF
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class FieldPresenter(private val listener: Res) {

    data class FieldData(
        val fieldId: Int,
        val name: String,
        val parentId: Int,
        val child: ArrayList<FieldData>
    )

    interface Res {
        fun result(ok: Boolean, message: String, data: ArrayList<FieldData>?) {}
    }

    private interface Api {
        @FormUrlEncoded
        @POST("student/studentField/index.php/")
        fun request(
            @Field("phone") phone: String,
            @Field("apiCode") ac: String
        ): Call<ServerRes>
    }

    fun get() {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        val api: Api = ApiClient.getClient().create(Api::class.java)
        val req = api.request(phone, ac)
        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.result(false, "error, pleaseTryAgain!", null)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                val res = response.body()
                val data = ArrayList<FieldData>()
                res?.let {
                    it.data.forEach {
                        data.add(Gson().fromJson(it, FieldData::class.java))
                    }
                }
                listener.result(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1), data)
            }
        })
    }

}