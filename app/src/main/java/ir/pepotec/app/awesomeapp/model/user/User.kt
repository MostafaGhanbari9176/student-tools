package ir.pepotec.app.awesomeapp.model.user

import ir.pepotec.app.awesomeapp.model.ApiClient
import ir.pepotec.app.awesomeapp.model.ServerRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class User (private val listener:UserListener){

    interface UserListener{
        fun createVerifyCodeRes(res:ServerRes?)
        fun checkVerifyCodeRes(res:ServerRes?)
        fun logInRes(res:ServerRes?)
        fun newPassRes(res:ServerRes?)
        fun logOut(res: ServerRes?)
        fun userError(message: String?)
    }

    fun createVerifyCode(phone:String)
    {
        val api:UserApi = ApiClient.getClient().create(UserApi::class.java)
        val req: Call<ServerRes> = api.createVerifyCode(phone)
        req.enqueue(object:Callback<ServerRes>{
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.userError(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
              listener.createVerifyCodeRes(response.body())
            }
        })

    }

    fun checkVerifyCode(phone: String, verifyCode:Int)
    {

        val api:UserApi = ApiClient.getClient().create(UserApi::class.java)
        val req:Call<ServerRes> = api.checkVerifyCode(phone, verifyCode)

        req.enqueue(object: Callback<ServerRes>{
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.userError(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.checkVerifyCodeRes(response.body())
            }
        })

    }

    fun logIn(phone: String, pass:String)
    {
        val api:UserApi = ApiClient.getClient().create(UserApi::class.java)
        val req:Call<ServerRes> = api.logIn(phone, pass)

        req.enqueue(object: Callback<ServerRes>{
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.userError(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.logInRes(response.body())
            }
        })
    }

    fun newPass(phone:String, pass: String)
    {
        val api:UserApi = ApiClient.getClient().create(UserApi::class.java)
        val req:Call<ServerRes> = api.newPass(phone, pass)

        req.enqueue(object: Callback<ServerRes>{
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.userError(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.newPassRes(response.body())
            }
        })
    }

    fun logOut(phone:String, apiCode:String)
    {
        val api:UserApi = ApiClient.getClient().create(UserApi::class.java)
        val req:Call<ServerRes> = api.logOut(phone, apiCode)

        req.enqueue(object: Callback<ServerRes>{
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.userError(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.logOut(response.body())
            }
        })
    }


}