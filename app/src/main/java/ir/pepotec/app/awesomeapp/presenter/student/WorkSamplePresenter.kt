package ir.pepotec.app.awesomeapp.presenter.student

import com.google.gson.Gson
import ir.pepotec.app.awesomeapp.model.ServerRes
import ir.pepotec.app.awesomeapp.model.student.workSample.WorkSample
import ir.pepotec.app.awesomeapp.model.student.workSample.WorkSampleData
import ir.pepotec.app.awesomeapp.model.student.workSample.workSampleList
import ir.pepotec.app.awesomeapp.model.user.UserDb
import ir.pepotec.app.awesomeapp.view.uses.AF
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
            multiData.add(MultipartBody.Part.createFormData("img[]", "img[]", requestBody))
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
            multiData.add(MultipartBody.Part.createFormData("img[]", "img[]", requestBody))
        }
        WorkSample(this).editWorkSample(idBody, phoneBody, acBody, subjectBody, descriptionBody, multiData)
    }

    fun workSampleList(abilityId: Int, itsMy: Boolean) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        WorkSample(this).getWorkSampleList(phone, ac, abilityId, itsMy)
    }

    fun increaseSeen(workSampleId: Int) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        WorkSample(this).increaseSeen(workSampleId, phone, ac)
    }

    fun getWorkSample(id: Int, itsMy: Boolean) {
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
        listener.addWorkSampleResult(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1))
    }

    override fun workSampleListData(res: ServerRes?) {

        val data = ArrayList<workSampleList>()
        res?.let {
            for (o in it.data)
                data.add(Gson().fromJson(o, workSampleList::class.java))
        }
        listener.workSampleListData(
            res?.code == ServerRes.ok,
            res?.message ?: AF().serverMessage(res?.code ?: -1),
            data
        )

    }

    override fun workSampleData(res: ServerRes?) {
        val data = res?.let { Gson().fromJson(it.data[0], WorkSampleData::class.java) }
        listener.workSampleData(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1), data)

    }

    override fun editWorkSampleRes(res: ServerRes?) {
        listener.editWorkSampleResult(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1))
    }

    override fun eyeCloseWorkSampleRes(res: ServerRes?) {

    }

    override fun deleteWorkSampleRes(res: ServerRes?) {
        listener.deleteWorkSampleResult(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1))
    }

    override fun workSampleImgData(res: ByteArray?) {
        listener.workSampleImgData(res)
    }
}