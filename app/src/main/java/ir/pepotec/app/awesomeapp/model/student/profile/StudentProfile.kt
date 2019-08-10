package ir.pepotec.app.awesomeapp.model.student.profile

import ir.pepotec.app.awesomeapp.model.ApiClient
import ir.pepotec.app.awesomeapp.model.ServerRes
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentProfile(private val listener: StudentProfileResponse) {

    companion object {
        const val baseUrl = "student/profile/index.php/";
    }

    interface StudentProfileResponse {
        fun addStudentRes(res: ServerRes?)
        fun studentData(res: ServerRes?)
        fun otherProfileData(res: ServerRes?)
        fun studentImgData(data: ByteArray?)
        fun upStudentImgRes(ok: Boolean)
        fun friendListData(res: ServerRes?)
        fun addFriendRes(res: ServerRes?)
        fun saveAboutMeRes(res: ServerRes?)
        fun aboutMeData(res: ServerRes?)
        fun elNameRes(res: ServerRes?)
        fun elPhoneRes(res: ServerRes?)
        fun elImgRes(res: ServerRes?)
        fun searchRes(res: ServerRes?)
    }

    fun addStudent(phone: String, apiCode: String, sId: String, name: String, pass: String) {
        val api: StudentProfileApi = ApiClient.getClient().create(StudentProfileApi::class.java)
        val req = api.addStudent(phone, apiCode, sId, name, pass)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.addStudentRes(null)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.addStudentRes(response.body())
            }
        })
    }

    fun myProfile(phone: String, ac: String) {
        val api: StudentProfileApi = ApiClient.getClient().create(StudentProfileApi::class.java)
        val req = api.myProfile(phone, ac)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.studentData(null)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.studentData(response.body())
            }
        })
    }

    fun downMyImg(phone: String, ac: String) {
        val api: StudentProfileApi = ApiClient.getClient().create(StudentProfileApi::class.java)
        val req = api.downMyImg(phone, ac)

        req.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //listener.error(t.message)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                listener.studentImgData(response.body()?.bytes())
            }
        })
    }

    fun downOtherImg(phone: String, ac: String, userId: Int) {
        val api: StudentProfileApi = ApiClient.getClient().create(StudentProfileApi::class.java)
        val req = api.downOtherImg(phone, ac, userId)

        req.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //listener.error(t.message)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                listener.studentImgData(response.body()?.bytes())
            }
        })
    }

    fun upMyImg(phone: RequestBody, ac: RequestBody, file: MultipartBody.Part) {
        val api: StudentProfileApi = ApiClient.getClient().create(StudentProfileApi::class.java)
        val req = api.upMyImg(phone, ac, file)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                //listener.error(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.upStudentImgRes(response.isSuccessful)
            }
        })
    }

    fun getFriendList(phone: String, ac: String) {
        val api: StudentProfileApi = ApiClient.getClient().create(StudentProfileApi::class.java)
        val req = api.friendList(phone, ac)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.friendListData(null)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.friendListData(response.body())
            }
        })
    }

    fun addFriend(phone: String, ac: String, friendId: Int) {
        val api: StudentProfileApi = ApiClient.getClient().create(StudentProfileApi::class.java)
        val req = api.addFriend(phone, ac, friendId)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.addFriendRes(null)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.addFriendRes(response.body())
            }
        })
    }

    fun deleteFriend(phone: String, ac: String, friendId: Int) {
        val api: StudentProfileApi = ApiClient.getClient().create(StudentProfileApi::class.java)
        val req = api.deleteFriend(phone, ac, friendId)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.addFriendRes(null)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.addFriendRes(response.body())
            }
        })
    }

    fun saveAboutMe(phone: String, ac: String, text: String) {
        val api: StudentProfileApi = ApiClient.getClient().create(StudentProfileApi::class.java)
        val req = api.saveAboutMe(phone, ac, text)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                //listener.error(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                //listener.saveAboutMeRes(response.body())
            }
        })
    }

    fun getAboutMe(phone: String, ac: String) {
        val api: StudentProfileApi = ApiClient.getClient().create(StudentProfileApi::class.java)
        val req = api.getAboutMe(phone, ac)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.aboutMeData(null)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.aboutMeData(response.body())
            }
        })
    }

    fun eLName(phone: String, ac: String, code: Int) {
        val api: StudentProfileApi = ApiClient.getClient().create(StudentProfileApi::class.java)
        val req = api.eLName(phone, ac, code)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.elNameRes(null)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.elNameRes(response.body())
            }
        })
    }

    fun eLPhone(phone: String, ac: String, code: Int) {
        val api: StudentProfileApi = ApiClient.getClient().create(StudentProfileApi::class.java)
        val req = api.eLPhone(phone, ac, code)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.elPhoneRes(null)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.elPhoneRes(response.body())
            }
        })
    }

    fun eLImg(phone: String, ac: String, code: Int) {
        val api: StudentProfileApi = ApiClient.getClient().create(StudentProfileApi::class.java)
        val req = api.eLImg(phone, ac, code)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.elImgRes(null)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.elImgRes(response.body())
            }
        })
    }

    fun search(phone: String, ac: String, key: String, num: Int, step: Int) {
        val api: StudentProfileApi = ApiClient.getClient().create(StudentProfileApi::class.java)
        val req = api.search(phone, ac, key, num, step)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.searchRes(null)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.searchRes(response.body())
            }
        })
    }

    fun getOtherProfile(phone: String, ac: String, userId: Int) {
        val api: StudentProfileApi = ApiClient.getClient().create(StudentProfileApi::class.java)
        val req = api.getOtherProfile(phone, ac, userId)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.otherProfileData(null)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.otherProfileData(response.body())
            }
        })
    }

}