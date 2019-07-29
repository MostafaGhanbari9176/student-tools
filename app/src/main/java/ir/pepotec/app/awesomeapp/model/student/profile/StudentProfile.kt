package ir.pepotec.app.awesomeapp.model.student.profile

import ir.pepotec.app.awesomeapp.model.ApiClient
import ir.pepotec.app.awesomeapp.model.ServerRes
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentProfile(private val listener:StudentProfileResponse) {

    interface StudentProfileResponse
    {
        fun addStudentRes(res:ServerRes?)
        fun studentData(res:ServerRes?)
        fun studentImgData(data:ByteArray?)
        fun upStudentImgRes(ok:Boolean)
        fun friendListData(res:ServerRes?)
        fun addFriendRes(res:ServerRes?)
        fun saveAboutMeRes(res:ServerRes?)
        fun aboutMeData(res:ServerRes?)
        fun elNameRes(res:ServerRes?)
        fun elPhoneRes(res:ServerRes?)
        fun elImgRes(res:ServerRes?)
        fun error(message:String?)
    }

    fun addStudent(phone:String, apiCode:String, sId:String, name:String, pass:String)
    {
        val api:StudentProfileApi = ApiClient.getClient().create(StudentProfileApi::class.java)
        val req = api.addStudent(phone, apiCode, sId, name, pass)

        req.enqueue(object: Callback<ServerRes>{
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.error(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.addStudentRes(response.body())
            }
        })
    }

    fun getStudent(phone:String, ac:String)
    {
        val api:StudentProfileApi = ApiClient.getClient().create(StudentProfileApi::class.java)
        val req = api.getStudent(phone, ac)

        req.enqueue(object: Callback<ServerRes>{
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.error(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
               listener.studentData(response.body())
            }
        })
    }

    fun downStudentImg(phone:String, ac:String)
    {
        val api:StudentProfileApi = ApiClient.getClient().create(StudentProfileApi::class.java)
        val req = api.downStudentImg(phone, ac)

        req.enqueue(object: Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //listener.error(t.message)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                listener.studentImgData(response.body()?.bytes())
            }
        })
    }

    fun upStudentImg(phone:String, ac:String, file:MultipartBody.Part)
    {
        val api:StudentProfileApi = ApiClient.getClient().create(StudentProfileApi::class.java)
        val req = api.upStudentImg(phone, ac, file)

        req.enqueue(object: Callback<ServerRes>{
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                //listener.error(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
               listener.upStudentImgRes(response.isSuccessful)
            }
        })
    }

    fun getFriendList(phone:String, ac:String)
    {
        val api:StudentProfileApi = ApiClient.getClient().create(StudentProfileApi::class.java)
        val req = api.friendList(phone, ac)

        req.enqueue(object: Callback<ServerRes>{
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.error(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.friendListData(response.body())
            }
        })
    }

    fun addFriend(phone:String, ac:String, friendId:String)
    {
        val api:StudentProfileApi = ApiClient.getClient().create(StudentProfileApi::class.java)
        val req = api.addFriend(phone, ac, friendId)

        req.enqueue(object: Callback<ServerRes>{
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.error(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.addFriendRes(response.body())
            }
        })
    }

    fun saveAboutMe(phone:String, ac:String, text:String)
    {
        val api:StudentProfileApi = ApiClient.getClient().create(StudentProfileApi::class.java)
        val req = api.saveAboutMe(phone, ac, text)

        req.enqueue(object: Callback<ServerRes>{
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                //listener.error(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
               //listener.saveAboutMeRes(response.body())
            }
        })
    }

    fun getAboutMe(phone:String, ac:String)
    {
        val api:StudentProfileApi = ApiClient.getClient().create(StudentProfileApi::class.java)
        val req = api.getAboutMe(phone, ac)

        req.enqueue(object: Callback<ServerRes>{
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.error(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
            listener.aboutMeData(response.body())
            }
        })
    }

    fun eLName(phone:String, ac:String, code:Int)
    {
        val api:StudentProfileApi = ApiClient.getClient().create(StudentProfileApi::class.java)
        val req = api.eLName(phone, ac, code)

        req.enqueue(object: Callback<ServerRes>{
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                //listener.error(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.elNameRes(response.body())
            }
        })
    }

    fun eLPhone(phone:String, ac:String, code:Int)
    {
        val api:StudentProfileApi = ApiClient.getClient().create(StudentProfileApi::class.java)
        val req = api.eLPhone(phone, ac, code)

        req.enqueue(object: Callback<ServerRes>{
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                //listener.error(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.elPhoneRes(response.body())
            }
        })
    }

    fun eLImg(phone:String, ac:String, code:Int)
    {
        val api:StudentProfileApi = ApiClient.getClient().create(StudentProfileApi::class.java)
        val req = api.eLImg(phone, ac, code)

        req.enqueue(object: Callback<ServerRes>{
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.error(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.elImgRes(response.body())
            }
        })
    }


}