package ir.pepotec.app.awesomeapp.presenter.student

import com.google.gson.Gson
import ir.pepotec.app.awesomeapp.model.ServerRes
import ir.pepotec.app.awesomeapp.model.ServerResConst
import ir.pepotec.app.awesomeapp.model.student.ability.AbilityList
import ir.pepotec.app.awesomeapp.model.student.profile.StudentProfile
import ir.pepotec.app.awesomeapp.model.student.profile.StudentProfileData
import ir.pepotec.app.awesomeapp.model.student.profile.StudentProfileDb
import ir.pepotec.app.awesomeapp.model.user.UserDb
import ir.pepotec.app.awesomeapp.view.uses.App
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class StudentProfilePresenter(private val listener: StudentProfileResult) : StudentProfile.StudentProfileResponse {

    interface StudentProfileResult {
        fun addStudentRes(ok: Boolean, message: String) {}
        fun studentData(
            ok: Boolean,
            message: String,
            data: StudentProfileData?,
            abilityData: ArrayList<AbilityList>? = null
        ) {
        }

        fun studentImgData(data: ByteArray?) {}
        fun upStudentImgRes(ok: Boolean) {}
        fun friendListData(ok: Boolean, message: String, data: ArrayList<StudentProfileData>?) {}
        fun addFriendRes(ok: Boolean, message: String) {}
        fun saveAboutMeRes(ok: Boolean, message: String) {}
        fun aboutMeData(ok: Boolean, message: String, data: String?) {}
        fun elNameRes(ok: Boolean, message: String, data: Int?) {}
        fun elPhoneRes(ok: Boolean, message: String, data: Int?) {}
        fun elImgRes(ok: Boolean, message: String, data: Int?) {}
        fun searchRes(ok: Boolean, message: String, data: ArrayList<StudentProfileData>?) {}
    }

    fun addStudent(sId: String, name: String, pass: String) {

        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        //StudentProfileDb().saveData(sId,user_name)
        StudentProfile(this).addStudent(phone, ac, sId, name, pass)
    }

    fun search(key: String, num: Int, step: Int) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        StudentProfile(this).search(phone, ac, key, num, step)
    }

    fun myProfile() {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        StudentProfile(this).myProfile(phone, ac)
    }

    fun downMyImg() {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        StudentProfile(this).downMyImg(phone, ac)
    }

    fun downOtherImg(userId: Int) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        StudentProfile(this).downOtherImg(phone, ac, userId)
    }

    fun upMyImg(file: File) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()

        val phoneBody = RequestBody.create(MultipartBody.FORM, phone)
        val acBody = RequestBody.create(MultipartBody.FORM, ac)

        val body = RequestBody.create(MediaType.parse(".jpg"), file)
        val multiData = MultipartBody.Part.createFormData("img", "img", body)
        StudentProfile(this).upMyImg(phoneBody, acBody, multiData)
    }

    fun getFriendList() {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        StudentProfile(this).getFriendList(phone, ac)
    }

    fun addFriend(friendId: Int) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        StudentProfile(this).addFriend(phone, ac, friendId)
    }

    fun deleteFriend(friendId: Int) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        StudentProfile(this).deleteFriend(phone, ac, friendId)
    }

    fun saveAboutMe(text: String) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        StudentProfile(this).saveAboutMe(phone, ac, text)
    }

    fun getAboutMe() {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        StudentProfile(this).getAboutMe(phone, ac)
    }

    fun eLName(code: Int) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        StudentProfile(this).eLName(phone, ac, code)
    }

    fun eLPhone(code: Int) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        StudentProfile(this).eLPhone(phone, ac, code)
    }

    fun eLImg(code: Int) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        StudentProfile(this).eLImg(phone, ac, code)
    }


    fun otherProfile(userId: Int) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        StudentProfile(this).getOtherProfile(phone, ac, userId)
    }

    override fun addStudentRes(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.addStudentRes(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!")
            ServerResConst.error -> listener.addStudentRes(
                false,
                "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!"
            )
            ServerResConst.ok -> listener.addStudentRes(true, "عملیات موفق بود.")
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun studentData(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.studentData(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!", null)
            ServerResConst.error -> listener.studentData(
                false,
                "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!",
                null
            )
            ServerResConst.ok -> {
                val json = res!!.data[0]
                val data = Gson().fromJson(json, StudentProfileData::class.java)
                StudentProfileDb().saveData(data)
                listener.studentData(true, "", data)
            }
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun searchRes(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.searchRes(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!", null)
            ServerResConst.error -> listener.searchRes(
                false,
                "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!",
                null
            )
            ServerResConst.ok -> {
                val data = ArrayList<StudentProfileData>()
                for (o in res?.data!!)
                    data.add(Gson().fromJson(o, StudentProfileData::class.java))

                listener.searchRes(true, "", data)
            }
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun studentImgData(data: ByteArray?) {
        listener.studentImgData(data)
    }

    override fun upStudentImgRes(ok: Boolean) {
        listener.upStudentImgRes(ok)
    }

    override fun friendListData(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.friendListData(
                false,
                "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!",
                null
            )
            ServerResConst.error -> listener.friendListData(
                false,
                "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!",
                null
            )
            ServerResConst.ok -> {
                val data = ArrayList<StudentProfileData>()
                val dataArray = res!!.data
                for (i in dataArray.indices) {
                    val json = dataArray[i]
                    data.add(Gson().fromJson(json, StudentProfileData::class.java))
                }
                listener.friendListData(true, "", data)
            }
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun addFriendRes(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.addFriendRes(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!")
            ServerResConst.error -> listener.addFriendRes(
                false,
                "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!"
            )
            ServerResConst.ok -> listener.addFriendRes(true, "انجام شد.")
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun saveAboutMeRes(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.saveAboutMeRes(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!")
            ServerResConst.error -> listener.saveAboutMeRes(
                false,
                "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!"
            )
            ServerResConst.ok -> listener.saveAboutMeRes(true, "عملیات موفق بود.")
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun aboutMeData(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.aboutMeData(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!", null)
            ServerResConst.error -> listener.aboutMeData(
                false,
                "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!",
                null
            )
            ServerResConst.ok -> {
                val data = res!!.data[0]
                listener.aboutMeData(true, "", data)
            }
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun elNameRes(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.elNameRes(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!", null)
            ServerResConst.error -> listener.elNameRes(
                false,
                "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!",
                null
            )
            ServerResConst.ok -> {
                val data = res!!.data[0].toInt()
                listener.elNameRes(true, "", data)
            }
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun elPhoneRes(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.elPhoneRes(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!", null)
            ServerResConst.error -> listener.elPhoneRes(
                false,
                "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!",
                null
            )
            ServerResConst.ok -> {
                val data = res!!.data[0].toInt()
                listener.elPhoneRes(true, "", data)
            }
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun elImgRes(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.elImgRes(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!", null)
            ServerResConst.error -> listener.elImgRes(
                false,
                "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!",
                null
            )
            ServerResConst.ok -> {
                val data = res!!.data[0].toInt()
                listener.elImgRes(true, "", data)
            }
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun otherProfileData(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.studentData(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!", null)
            ServerResConst.error -> listener.studentData(
                false,
                "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!",
                null
            )
            ServerResConst.ok -> {
                val abilityData = ArrayList<AbilityList>()
                var data: StudentProfileData? = null
                val resData = res!!.data
                for (i in (resData.indices)) {
                    if (i == 0)
                        data = Gson().fromJson(resData[0], StudentProfileData::class.java)
                    else
                    abilityData.add(Gson().fromJson(resData[i], AbilityList::class.java))
                }
                listener.studentData(true, "", data, abilityData)
            }
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

}