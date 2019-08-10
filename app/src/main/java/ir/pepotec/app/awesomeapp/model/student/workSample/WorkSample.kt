package ir.pepotec.app.awesomeapp.model.student.workSample

import ir.pepotec.app.awesomeapp.model.ApiClient
import ir.pepotec.app.awesomeapp.model.ServerRes
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkSample(private val listener: WorkSampleRes) {

    companion object {
        const val baseUrl = "student/workSample/index.php/"
    }

    interface WorkSampleRes {
        fun addWorkSampleRes(res: ServerRes?)
        fun workSampleListData(res: ServerRes?)
        fun workSampleData(res: ServerRes?)
        fun editWorkSampleRes(res: ServerRes?)
        fun eyeCloseWorkSampleRes(res: ServerRes?)
        fun deleteWorkSampleRes(res: ServerRes?)
        fun workSampleImgData(res: ByteArray?)
    }

    fun addWorkSample(
        phone: RequestBody,
        apiCode: RequestBody,
        abilityId: RequestBody,
        subject: RequestBody,
        description: RequestBody,
        files: ArrayList<MultipartBody.Part>
    ) {
        val api: WorkSampleApi = ApiClient.getClient().create(WorkSampleApi::class.java)
        val req = api.addWorkSample(phone, apiCode, abilityId, subject, description, files)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.addWorkSampleRes(null)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                with(response) {
                    listener.addWorkSampleRes(body())
                }
            }
        })
    }

    fun getWorkSampleList(phone: String, apiCode: String, abilityId: Int, itsMy: Boolean) {
        val api: WorkSampleApi = ApiClient.getClient().create(WorkSampleApi::class.java)
        val req = when (itsMy) {
            true -> api.getMyWorkSampleList(phone, apiCode, abilityId)
            else -> api.getOtherWorkSampleList(phone, apiCode, abilityId)
        }
        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.workSampleListData(null)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.workSampleListData(response.body())
            }
        })
    }


    fun getWorkSample(id: Int, phone: String, apiCode: String, itsMy: Boolean) {
        val api: WorkSampleApi = ApiClient.getClient().create(WorkSampleApi::class.java)
        val req = when (itsMy) {
            true -> api.getMyWorkSample(id, phone, apiCode)
            else -> api.getOtherWorkSample(id, phone, apiCode)
        }
        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.workSampleData(null)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.workSampleData(response.body())
            }
        })
    }

    fun increaseSeen(id: Int, phone: String, apiCode: String) {
        val api: WorkSampleApi = ApiClient.getClient().create(WorkSampleApi::class.java)
        val req =api.increaseSeen(id, phone, apiCode)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
            }
        })
    }

    fun editWorkSample(
        id: RequestBody,
        phone: RequestBody,
        apiCode: RequestBody,
        subject: RequestBody,
        description: RequestBody,
        files: ArrayList<MultipartBody.Part>
    ) {
        val api: WorkSampleApi = ApiClient.getClient().create(WorkSampleApi::class.java)
        val req = api.editWorkSample(id, phone, apiCode, subject, description, files)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.editWorkSampleRes(null)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.editWorkSampleRes(response.body())
            }
        })
    }

    fun deleteWorkSample(id: Int, phone: String, apiCode: String) {
        val api: WorkSampleApi = ApiClient.getClient().create(WorkSampleApi::class.java)
        val req = api.deleteWorkSample(id, phone, apiCode)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.deleteWorkSampleRes(null)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.deleteWorkSampleRes(response.body())
            }
        })
    }

    fun likeWorkSample(id: Int, phone: String, apiCode: String) {
        val api: WorkSampleApi = ApiClient.getClient().create(WorkSampleApi::class.java)
        val req = api.likeWorkSample(id, phone, apiCode)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {

            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {

            }
        })
    }

    fun workSampleImg(imgId: String, phone: String, apiCode: String) {
        val api: WorkSampleApi = ApiClient.getClient().create(WorkSampleApi::class.java)
        val req = api.workSampleImg(imgId, phone, apiCode)

        req.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                listener.workSampleImgData(null)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                listener.workSampleImgData(response.body()?.bytes())
            }
        })
    }

}