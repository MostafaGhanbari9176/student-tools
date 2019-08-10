package ir.pepotec.app.awesomeapp.model.user

import ir.pepotec.app.awesomeapp.model.ApiClient
import ir.pepotec.app.awesomeapp.model.ServerRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class User (private val listener:UserResponse){

    companion object {
        const val baseUrl = "user/index.php/"
    }

    interface UserResponse{
        fun createVerifyCodeRes(res:ServerRes?)
        fun signUpRes(res:ServerRes?)
        fun logInRes(res:ServerRes?)
        fun newPassRes(res:ServerRes?)
        fun changePassRes(res:ServerRes?)
        fun logOut(res: ServerRes?)
    }

    fun sendVerifyCode(phone:String)
    {
        val api:UserApi = ApiClient.getClient().create(UserApi::class.java)
        val req: Call<ServerRes> = api.sendVerifyCode(phone)
        req.enqueue(object:Callback<ServerRes>{
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.createVerifyCodeRes(null)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
              listener.createVerifyCodeRes(response.body())
            }
        })

    }

    fun signUp(phone: String, verifyCode:String, kind:Int)
    {

        val api:UserApi = ApiClient.getClient().create(UserApi::class.java)
        val req:Call<ServerRes> = api.signUp(phone, verifyCode, kind)

        req.enqueue(object: Callback<ServerRes>{
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.signUpRes(null)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.signUpRes(response.body())
            }
        })

    }

    fun logIn(phone: String, pass:String)
    {
        val api:UserApi = ApiClient.getClient().create(UserApi::class.java)
        val req:Call<ServerRes> = api.logIn(phone, pass)

        req.enqueue(object: Callback<ServerRes>{
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.logInRes(null)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.logInRes(response.body())
            }
        })
    }

    fun newPass(phone:String, verifyCode: String)
    {
        val api:UserApi = ApiClient.getClient().create(UserApi::class.java)
        val req:Call<ServerRes> = api.newPass(phone, verifyCode)

        req.enqueue(object: Callback<ServerRes>{
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.newPassRes(null)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.newPassRes(response.body())
            }
        })
    }

    fun changePass(phone:String, pass: String, apiCode:String)
    {
        val api:UserApi = ApiClient.getClient().create(UserApi::class.java)
        val req:Call<ServerRes> = api.changePass(phone, apiCode, pass)

        req.enqueue(object: Callback<ServerRes>{
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.changePassRes(null)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.changePassRes(response.body())
            }
        })
    }

    fun logOut(phone:String, apiCode:String)
    {
        val api:UserApi = ApiClient.getClient().create(UserApi::class.java)
        val req:Call<ServerRes> = api.logOut(phone, apiCode)

        req.enqueue(object: Callback<ServerRes>{
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.logOut(null)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.logOut(response.body())
            }
        })
    }


}