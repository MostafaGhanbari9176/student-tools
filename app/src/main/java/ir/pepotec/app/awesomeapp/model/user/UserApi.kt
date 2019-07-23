package ir.pepotec.app.awesomeapp.model.user

import ir.pepotec.app.awesomeapp.model.ServerRes
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.Field

interface UserApi {

    @FormUrlEncoded
    @POST("createVerifyCode")
    fun createVerifyCode(@Field("phone") phone: String): Call<ServerRes>

    @FormUrlEncoded
    @POST("checkVerifyCode")
    fun checkVerifyCode(@Field("phone") phone: String, @Field("code") code: Int): Call<ServerRes>

    @FormUrlEncoded
    @POST("logIn")
    fun logIn(@Field("phone") phone: String, @Field("pass") pass: String): Call<ServerRes>

    @FormUrlEncoded
    @POST("newPass")
    fun newPass(@Field("phone") phone: String, @Field("pass") pass: String): Call<ServerRes>

    @FormUrlEncoded
    @POST("logOut")
    fun logOut(@Field("phone") phone: String, @Field("apiCode") apiCode: String): Call<ServerRes>
}