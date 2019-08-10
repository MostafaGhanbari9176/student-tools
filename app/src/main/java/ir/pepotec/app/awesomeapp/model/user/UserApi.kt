package ir.pepotec.app.awesomeapp.model.user

import ir.pepotec.app.awesomeapp.model.ServerRes
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.Field

interface UserApi {

    @FormUrlEncoded
    @POST("${User.baseUrl}sendVerifyCode")
    fun sendVerifyCode(@Field("phone") phone: String): Call<ServerRes>

    @FormUrlEncoded
    @POST("${User.baseUrl}signUp")
    fun signUp(@Field("phone") phone: String, @Field("code") code: String, @Field("kind") kind: Int): Call<ServerRes>

    @FormUrlEncoded
    @POST("${User.baseUrl}logIn")
    fun logIn(@Field("phone") phone: String, @Field("pass") pass: String): Call<ServerRes>

    @FormUrlEncoded
    @POST("${User.baseUrl}newPass")
    fun newPass(@Field("phone") phone: String, @Field("code") verifyCode: String): Call<ServerRes>

    @FormUrlEncoded
    @POST("${User.baseUrl}changePass")
    fun changePass(@Field("phone") phone: String, @Field("apiCode") apiCode: String, @Field("pass") pass: String): Call<ServerRes>

    @FormUrlEncoded
    @POST("${User.baseUrl}logOut")
    fun logOut(@Field("phone") phone: String, @Field("ac") apiCode: String): Call<ServerRes>
}