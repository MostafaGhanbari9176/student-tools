package ir.pepotec.app.awesomeapp.model.student.workSample

import ir.pepotec.app.awesomeapp.model.ApiClient
import ir.pepotec.app.awesomeapp.model.ServerRes
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkSample(private val listener: WorkSampleRes) {

    interface WorkSampleRes {
        fun addWorkSampleRes(res: ServerRes?)
        fun workSampleListData(res: ServerRes?)
        fun workSampleData(res: ServerRes?)
        fun editWorkSampleRes(res: ServerRes?)
        fun eyeCloseWorkSampleRes(res: ServerRes?)
        fun deleteWorkSampleRes(res: ServerRes?)
        fun workSampleImgData(res: ByteArray?)
        fun error(message: String?)
    }

    fun addWorkSample(
        phone: String,
        apiCode: String,
        subject: String,
        description: String,
        files: ArrayList<MultipartBody.Part>
    ) {
        val api: WorkSampleApi = ApiClient.getClient().create(WorkSampleApi::class.java)
        val req = api.addWorkSample(phone, apiCode, subject, description, files)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.error(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                with(response) {
                    listener.addWorkSampleRes(body())
                }
            }
        })
    }

    fun getWorkSampleList(phone: String, apiCode: String, abilityId: String) {
        val api: WorkSampleApi = ApiClient.getClient().create(WorkSampleApi::class.java)
        val req = api.getWorkSampleList(phone, apiCode, abilityId)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.error(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.workSampleListData(response.body())
            }
        })
    }

    fun getWorkSample(id: String, phone: String, apiCode: String) {
        val api: WorkSampleApi = ApiClient.getClient().create(WorkSampleApi::class.java)
        val req = api.getWorkSample(id, phone, apiCode)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.error(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.workSampleData(response.body())
            }
        })
    }

    fun editWorkSample(
        id: String,
        phone: String,
        apiCode: String,
        subject: String,
        description: String,
        files: ArrayList<MultipartBody.Part>
    ) {
        val api: WorkSampleApi = ApiClient.getClient().create(WorkSampleApi::class.java)
        val req = api.editWorkSample(id, phone, apiCode, subject, description, files)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.error(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.editWorkSampleRes(response.body())
            }
        })
    }

    fun eyeCloseWorkSample(id: String, phone: String, apiCode: String) {
        val api: WorkSampleApi = ApiClient.getClient().create(WorkSampleApi::class.java)
        val req = api.eyeCloseWorkSample(id, phone, apiCode)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.error(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.eyeCloseWorkSampleRes(response.body())
            }
        })
    }

    fun deleteWorkSample(id: String, phone: String, apiCode: String) {
        val api: WorkSampleApi = ApiClient.getClient().create(WorkSampleApi::class.java)
        val req = api.deleteWorkSample(id, phone, apiCode)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.error(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.deleteWorkSampleRes(response.body())
            }
        })
    }

    fun workSampleImg(imgId: String, phone: String, apiCode: String) {
        val api: WorkSampleApi = ApiClient.getClient().create(WorkSampleApi::class.java)
        val req = api.workSampleImg(imgId, phone, apiCode)

        req.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
               // listener.error(t.message)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                listener.workSampleImgData(response.body()?.bytes())
            }
        })
    }

}