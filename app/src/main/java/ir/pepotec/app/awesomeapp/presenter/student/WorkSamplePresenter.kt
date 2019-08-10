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

class WorkSamplePresenter(private val listener: WorkSampleResult) : WorkSample.WorkSampleRes {

    interface WorkSampleResult {
        fun addWorkSampleResult(ok: Boolean, message: String) {}
        fun workSampleListData(ok: Boolean, message: String, data: ArrayList<workSampleList>?) {}
        fun workSampleData(ok: Boolean, message: String, data: WorkSampleData?) {}
        fun editWorkSampleResult(ok: Boolean, message: String) {}
        fun eyeCloseWorkSampleResult(ok: Boolean, message: String) {}
        fun deleteWorkSampleResult(ok: Boolean, message: String) {}
        fun workSampleImgData(data: ByteArray?) {}
    }

    fun addWorkSample(abilityId: Int, subject: String, description: String, files: ArrayList<File>) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()

        val phoneBody = RequestBody.create(MultipartBody.FORM, phone)
        val acBody = RequestBody.create(MultipartBody.FORM, ac)
        val subjectBody = RequestBody.create(MultipartBody.FORM, subject)
        val descriptionBody = RequestBody.create(MultipartBody.FORM, description)
        val abilityIdBody = RequestBody.create(MultipartBody.FORM, abilityId.toString())

        val multiData = ArrayList<MultipartBody.Part>()
        for (o in files) {
            val requestBody = RequestBody.create(MediaType.parse(".jpg"), o)
            multiData.add(MultipartBody.Part.createFormData("img[]","img[]", requestBody))
        }
        WorkSample(this).addWorkSample(phoneBody, acBody, abilityIdBody, subjectBody, descriptionBody, multiData)
    }

    fun editWorkSample(id: Int, subject: String, description: String, files: ArrayList<File>) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()

        val phoneBody = RequestBody.create(MultipartBody.FORM, phone)
        val acBody = RequestBody.create(MultipartBody.FORM, ac)
        val subjectBody = RequestBody.create(MultipartBody.FORM, subject)
        val descriptionBody = RequestBody.create(MultipartBody.FORM, description)
        val idBody = RequestBody.create(MultipartBody.FORM, id.toString())

        val multiData = ArrayList<MultipartBody.Part>()
        for (o in files) {
            val requestBody = RequestBody.create(MediaType.parse(".jpg"), o)
            multiData.add(MultipartBody.Part.createFormData("img[]","img[]", requestBody))
        }
        WorkSample(this).editWorkSample(idBody, phoneBody, acBody, subjectBody, descriptionBody, multiData)
    }

    fun workSampleList(abilityId: Int, itsMy:Boolean) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        WorkSample(this).getWorkSampleList(phone, ac, abilityId, itsMy)
    }

    fun increaseSeen(workSampleId: Int) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        WorkSample(this).increaseSeen(workSampleId, phone, ac)
    }

    fun getWorkSample(id: Int, itsMy:Boolean) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        WorkSample(this).getWorkSample(id, phone, ac, itsMy)
    }

    fun deleteWorkSample(id: Int) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        WorkSample(this).deleteWorkSample(id, phone, ac)
    }

    fun likeWorkSample(id: Int) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        WorkSample(this).likeWorkSample(id, phone, ac)
    }

    fun workSampleImg(imgId: String) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        WorkSample(this).workSampleImg(imgId, phone, ac)
    }

    override fun addWorkSampleRes(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.addWorkSampleResult(
                false,
                "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!"
            )
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
            -1 -> listener.workSampleListData(
                false,
                "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!",
                null
            )
            ServerResConst.error -> listener.workSampleListData(
                false,
                "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!",
                null
            )
            ServerResConst.ok -> {
                val jsonArray = res!!.data
                val data = ArrayList<workSampleList>()
                for (o in jsonArray)
                    data.add(Gson().fromJson(o, workSampleList::class.java))
                listener.workSampleListData(true, "", data)
            }
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun workSampleData(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.workSampleData(
                false,
                "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!",
                null
            )
            ServerResConst.error -> listener.workSampleData(
                false,
                "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!",
                null
            )
            ServerResConst.ok -> {
                val json = res!!.data[0]
                val data = Gson().fromJson(json, WorkSampleData::class.java)
                listener.workSampleData(true, "", data)
            }
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun editWorkSampleRes(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.editWorkSampleResult(
                false,
                "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!"
            )
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
            -1 -> listener.eyeCloseWorkSampleResult(
                false,
                "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!"
            )
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
            -1 -> listener.deleteWorkSampleResult(
                false,
                "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!"
            )
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
}