package ir.pepotec.app.awesomeapp.model.student.profile

import ir.pepotec.app.awesomeapp.model.ServerRes
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface StudentProfileApi {

    @FormUrlEncoded
    @POST("${StudentProfile.baseUrl}addStudent")
    fun addStudent(
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String,
        @Field("sId") studentId: String,
        @Field("user_name") name: String,
        @Field("pass") pass: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${StudentProfile.baseUrl}myProfile")
    fun myProfile(
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${StudentProfile.baseUrl}downMyImg")
    fun downMyImg(
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("${StudentProfile.baseUrl}downImg")
    fun downOtherImg(
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String,
        @Field("otherId") userId: Int
    ): Call<ResponseBody>

    @Multipart
    @POST("${StudentProfile.baseUrl}upMyImg")
    fun upMyImg(
        @Part("phone") phone: RequestBody,
        @Part("apiCode") apiCode: RequestBody,
        @Part img: MultipartBody.Part
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${StudentProfile.baseUrl}friendList")
    fun friendList(
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${StudentProfile.baseUrl}addFriend")
    fun addFriend(
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String,
        @Field("otherId") friendId: Int
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${StudentProfile.baseUrl}deleteFriend")
    fun deleteFriend(
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String,
        @Field("otherId") friendId: Int
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${StudentProfile.baseUrl}saveAboutMe")
    fun saveAboutMe(
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String,
        @Field("text") text: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${StudentProfile.baseUrl}getAboutMe")
    fun getAboutMe(
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${StudentProfile.baseUrl}eLName")
    fun eLName(
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String,
        @Field("code") hideCode: Int
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${StudentProfile.baseUrl}eLPhone")
    fun eLPhone(
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String,
        @Field("code") hideCode: Int
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${StudentProfile.baseUrl}eLImg")
    fun eLImg(
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String,
        @Field("code") hideCode: Int
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${StudentProfile.baseUrl}searchStudentById")
    fun search(
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String,
        @Field("key") key: String,
        @Field("num") num: Int,
        @Field("step") step: Int
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${StudentProfile.baseUrl}getProfile")
    fun getOtherProfile(
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String,
        @Field("otherId") otherId: Int
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${StudentProfile.baseUrl}chatList")
    fun getChatList(
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String
    ): Call<ServerRes>

}