package ir.pepotec.app.awesomeapp.model.course

import ir.pepotec.app.awesomeapp.model.ApiClient
import ir.pepotec.app.awesomeapp.model.ServerRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Course(private val listener: Res) : Callback<ServerRes> {

    interface Res {
        fun result(res: ServerRes?){}
    }

    companion object {
        const val baseUrl = "course/index.php/"
    }

    private val api: CourseApi = ApiClient.getClient().create(CourseApi::class.java)

     fun getFirstData(phone: String, apiCode: String) {
        api.getFirstData(phone, apiCode).apply { enqueue(this@Course) }
    }

     fun get(phone: String, apiCode: String, courseId: Int) {
        api.get(phone, apiCode, courseId).apply { enqueue(this@Course) }
    }

     fun getListData(phone: String, apiCode: String, gId: Int, num: Int, step: Int) {
        api.getListData(phone, apiCode, gId, num, step).apply { enqueue(this@Course) }
    }

     fun getSpecial(phone: String, apiCode: String) {
        api.getSpecial(phone, apiCode).apply { enqueue(this@Course) }
    }

    override fun onFailure(call: Call<ServerRes>, t: Throwable) {
        listener.result(null)
    }

    override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
        listener.result(response.body())
    }
}
