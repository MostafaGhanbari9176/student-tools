package ir.pepotec.app.awesomeapp.presenter.student

import com.google.gson.Gson
import ir.pepotec.app.awesomeapp.model.ServerRes
import ir.pepotec.app.awesomeapp.model.ServerResConst
import ir.pepotec.app.awesomeapp.model.student.workSample.WorkSample
import ir.pepotec.app.awesomeapp.model.student.workSample.WorkSampleData
import ir.pepotec.app.awesomeapp.model.student.workSample.workSampleList
import ir.pepotec.app.awesomeapp.model.user.UserDb
import ir.pepotec.app.awesomeapp.view.uses.App
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class WorkSamplePresenter(private val listener:WorkSampleResult):WorkSample.WorkSampleRes {

    interface WorkSampleResult{
        fun addWorkSampleResult(ok:Boolean, message: String){}
        fun workSampleListData(ok:Boolean, message: String, data: ArrayList<workSampleList>?){}
        fun workSampleData(ok:Boolean, message: String, data:WorkSampleData?){}
        fun editWorkSampleResult(ok:Boolean, message: String){}
        fun eyeCloseWorkSampleResult(ok:Boolean, message: String){}
        fun deleteWorkSampleResult(ok:Boolean, message: String){}
        fun workSampleImgData(data:ByteArray?){}
        fun workSampleError(message: String){}
    }

    fun addWorkSample(subject: String, description: String, files: ArrayList<File>)
    {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        val multiData = ArrayList<MultipartBody.Part>()
        for(o in files)
        {
            val requestBody = RequestBody.create(MediaType.parse(""), o)
            multiData.add(MultipartBody.Part.createFormData("FuckFace .|.. ",o.name,requestBody))
        }
        WorkSample(this).addWorkSample(phone, ac, subject, description, multiData)
    }

    fun editWorkSample(id:String, subject: String, description: String, files: ArrayList<File>)
    {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        val multiData = ArrayList<MultipartBody.Part>()
        for(o in files)
        {
            val requestBody = RequestBody.create(MediaType.parse(""), o)
            multiData.add(MultipartBody.Part.createFormData("FuckFace .|.. ",o.name,requestBody))
        }
        WorkSample(this).editWorkSample(id, phone, ac, subject, description, multiData)
    }

    fun workSampleList(abilityId: String)
    {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        WorkSample(this).getWorkSampleList(phone, ac, abilityId)
    }

    fun getWorkSample(id:String)
    {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        WorkSample(this).getWorkSample(id, phone, ac)
    }

    fun eyeCloseWorkSample(id:String)
    {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        WorkSample(this).eyeCloseWorkSample(id, phone, ac)
    }

    fun deleteWorkSample(id:String)
    {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        WorkSample(this).deleteWorkSample(id, phone ,ac)
    }

    fun workSampleImg(imgId:String)
    {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        WorkSample(this).workSampleImg(imgId, phone ,ac)
    }

    override fun addWorkSampleRes(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.addWorkSampleResult(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!")
            ServerResConst.error -> listener.addWorkSampleResult(
                false,
                "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!"
            )
            ServerResConst.ok -> listener.addWorkSampleResult(true, "عملیات موفق بود.")
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun workSampleListData(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.workSampleListData(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!", null)
            ServerResConst.error -> listener.workSampleListData(
                false,
                "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!",
                null
            )
            ServerResConst.ok ->{
                val jsonArray = res?.data
                val data = ArrayList<workSampleList>()
                for(i in 0 .. (jsonArray?.length() ?: 0))
                {
                    val json = jsonArray?.getJSONObject(i).toString()
                    data.add(Gson().fromJson(json, workSampleList::class.java))
                }
                listener.workSampleListData(true, "", data)
            }
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun workSampleData(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.workSampleData(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!",null)
            ServerResConst.error -> listener.workSampleData(
                false,
                "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!",
                null
            )
            ServerResConst.ok -> {
                val json = res?.data?.getJSONObject(0).toString()
                val data = Gson().fromJson(json, WorkSampleData::class.java)
                listener.workSampleData(true, "", data)
            }
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun editWorkSampleRes(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.editWorkSampleResult(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!")
            ServerResConst.error -> listener.editWorkSampleResult(
                false,
                "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!"
            )
            ServerResConst.ok -> listener.editWorkSampleResult(true, "عملیات موفق بود.")
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun eyeCloseWorkSampleRes(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.eyeCloseWorkSampleResult(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!")
            ServerResConst.error -> listener.eyeCloseWorkSampleResult(
                false,
                "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!"
            )
            ServerResConst.ok -> listener.eyeCloseWorkSampleResult(true, "عملیات موفق بود.")
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun deleteWorkSampleRes(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.deleteWorkSampleResult(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!")
            ServerResConst.error -> listener.deleteWorkSampleResult(
                false,
                "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!"
            )
            ServerResConst.ok -> listener.deleteWorkSampleResult(true, "عملیات موفق بود.")
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun workSampleImgData(res: ByteArray?) {
        listener.workSampleImgData(res)
    }

    override fun error(message: String?) {
        listener.workSampleError("خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!")
    }
}