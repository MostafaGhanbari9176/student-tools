package ir.pepotec.app.awesomeapp.presenter.student

import com.google.gson.Gson
import ir.pepotec.app.awesomeapp.model.ServerRes
import ir.pepotec.app.awesomeapp.model.student.ability.AbilityList
import ir.pepotec.app.awesomeapp.model.chat.ChatListData
import ir.pepotec.app.awesomeapp.model.student.profile.StudentProfile
import ir.pepotec.app.awesomeapp.model.student.profile.StudentProfileData
import ir.pepotec.app.awesomeapp.model.student.profile.StudentProfileDb
import ir.pepotec.app.awesomeapp.model.user.UserDb
import ir.pepotec.app.awesomeapp.view.uses.AF
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
        fun chatListRes(ok: Boolean, singleNum: Int, data: ArrayList<ChatListData>) {}
        fun result(ok: Boolean, message:String) {}
    }

    private val phone = UserDb().getUserPhone()
    private val ac = UserDb().getUserApiCode()

    fun addStudent(sId: String, name: String, pass: String, email:String, fieldId:Int) {

        //StudentProfileDb().saveData(sId,user_name)
        StudentProfile(this).addStudent(phone, ac, sId, name, pass, email, fieldId)
    }

    fun search(key: String, num: Int, step: Int) {

        StudentProfile(this).search(phone, ac, key, num, step)
    }

    fun myProfile() {

        StudentProfile(this).myProfile(phone, ac)
    }

    fun downMyImg() {

        StudentProfile(this).downMyImg(phone, ac)
    }

    fun downOtherImg(userId: Int) {

        StudentProfile(this).downOtherImg(phone, ac, userId)
    }

    fun upMyImg(file: File) {


        val phoneBody = RequestBody.create(MultipartBody.FORM, phone)
        val acBody = RequestBody.create(MultipartBody.FORM, ac)

        val body = RequestBody.create(MediaType.parse(".jpg"), file)
        val multiData = MultipartBody.Part.createFormData("img", "img", body)
        StudentProfile(this).upMyImg(phoneBody, acBody, multiData)
    }

    fun getFriendList() {

        StudentProfile(this).getFriendList(phone, ac)
    }

    fun addFriend(friendId: Int) {

        StudentProfile(this).addFriend(phone, ac, friendId)
    }

    fun deleteFriend(friendId: Int) {

        StudentProfile(this).deleteFriend(phone, ac, friendId)
    }

    fun saveAboutMe(text: String) {

        StudentProfile(this).saveAboutMe(phone, ac, text)
    }

    fun getAboutMe() {

        StudentProfile(this).getAboutMe(phone, ac)
    }

    fun eLName(code: Int) {

        StudentProfile(this).eLName(phone, ac, code)
    }

    fun eLPhone(code: Int) {

        StudentProfile(this).eLPhone(phone, ac, code)
    }

    fun eLImg(code: Int) {

        StudentProfile(this).eLImg(phone, ac, code)
    }

    fun otherProfile(userId: Int) {

        StudentProfile(this).getOtherProfile(phone, ac, userId)
    }

    fun getChatList() {

        StudentProfile(object : StudentProfile.StudentProfileResponse {
            override fun otherProfileData(res: ServerRes?) {
                val data = ArrayList<ChatListData>()
                var singleNum = 0
                res?.let {
                    for (i in it.data.indices) {
                        if (i == 0)
                            singleNum = it.data[0].toInt()
                        else
                            data.add(Gson().fromJson(it.data[i], ChatListData::class.java))
                    }
                }
                listener.chatListRes(res?.code == ServerRes.ok, singleNum, data)
            }
        }).getChatList(phone, ac)
    }

    fun changeOnline(value:Int)
    {
        StudentProfile(object:StudentProfile.StudentProfileResponse{}).changeOnline(phone ,ac, value)
    }

    fun lastSeen(userId: Int)
    {
        StudentProfile(object:StudentProfile.StudentProfileResponse{
            override fun result(res: ServerRes?) {
                listener.result(res?.code == ServerRes.ok, res?.message ?: "offline")
            }
        }).getLastSeen(phone, ac, userId)
    }

    override fun addStudentRes(res: ServerRes?) {
        if (res?.code == ServerRes.ok)
            StudentProfileDb().isLogIn = true
        listener.addStudentRes(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1))

    }

    override fun studentData(res: ServerRes?) {
        var data: StudentProfileData? = null
        res?.let {
            data = Gson().fromJson(it.data[0], StudentProfileData::class.java)
            StudentProfileDb().saveData(data!!)
        }

        listener.studentData(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1), data)
    }

    override fun searchRes(res: ServerRes?) {
        val data = ArrayList<StudentProfileData>()
        res?.let {
            for (o in it.data)
                data.add(Gson().fromJson(o, StudentProfileData::class.java))
        }
        listener.searchRes(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1), data)

    }

    override fun studentImgData(data: ByteArray?) {
        listener.studentImgData(data)
    }

    override fun upStudentImgRes(ok: Boolean) {
        listener.upStudentImgRes(ok)
    }

    override fun friendListData(res: ServerRes?) {
        val data = ArrayList<StudentProfileData>()
        res?.let {
            for (o in it.data)
                data.add(Gson().fromJson(o, StudentProfileData::class.java))
        }
        listener.friendListData(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1), data)
    }

    override fun addFriendRes(res: ServerRes?) {
        listener.addFriendRes(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1))
    }

    override fun saveAboutMeRes(res: ServerRes?) {
        listener.saveAboutMeRes(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1))
    }

    override fun aboutMeData(res: ServerRes?) {
        val data = res?.let { it.data[0] }
        listener.aboutMeData(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1), data)
    }

    override fun elNameRes(res: ServerRes?) {
        val data = res?.let { it.data[0].toInt() }
        listener.elNameRes(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1), data)
    }

    override fun elPhoneRes(res: ServerRes?) {
        val data = res?.let { it.data[0].toInt() }
        listener.elPhoneRes(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1), data)
    }

    override fun elImgRes(res: ServerRes?) {

        val data = res?.let { it.data[0].toInt() }
        listener.elImgRes(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1), data)

    }

    override fun otherProfileData(res: ServerRes?) {
        val abilityData = ArrayList<AbilityList>()
        var data: StudentProfileData? = null
        res?.let {
            for (i in (it.data.indices)) {
                if (i == 0)
                    data = Gson().fromJson(it.data[0], StudentProfileData::class.java)
                else
                    abilityData.add(Gson().fromJson(it.data[i], AbilityList::class.java))
            }
        }
        listener.studentData(
            res?.code == ServerRes.ok,
            res?.message ?: AF().serverMessage(res?.code ?: -1),
            data,
            abilityData
        )
    }

}