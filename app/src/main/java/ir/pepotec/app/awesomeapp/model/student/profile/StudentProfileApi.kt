package ir.pepotec.app.awesomeapp.model.student.profile

import ir.pepotec.app.awesomeapp.model.ServerRes
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface StudentProfileApi {

    @FormUrlEncoded
    @POST("addStudent")
    fun addStudent(
        @Field("phone") phone: String,
        @Field("ac") apiCode: String,
        @Field("sId") studentId: String,
        @Field("name") name: String,
        @Field("pass") pass: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("getStudent")
    fun getStudent(
        @Field("phone") phone: String,
        @Field("ac") apiCode: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("downStudentImg")
    fun downStudentImg(
        @Field("phone") phone: String,
        @Field("ac") apiCode: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @Multipart
    @POST("upStudentImg")
    fun upStudentImg(
        @Field("phone") phone: String,
        @Field("ac") apiCode: String,
        @Part img: MultipartBody.Part
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("friendList")
    fun friendList(
        @Field("phone") phone: String,
        @Field("ac") apiCode: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("addFriend")
    fun addFriend(
        @Field("phone") phone: String,
        @Field("ac") apiCode: String,
        @Field("friendId") friendId: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("saveAboutMe")
    fun saveAboutMe(
        @Field("phone") phone: String,
        @Field("ac") apiCode: String,
        @Field("text") text: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("getAboutMe")
    fun getAboutMe(
        @Field("phone") phone: String,
        @Field("ac") apiCode: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("eLName")
    fun eLName(
        @Field("phone") phone: String,
        @Field("ac") apiCode: String,
        @Field("code") hideCode: Int
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("eLPhone")
    fun eLPhone(
        @Field("phone") phone: String,
        @Field("ac") apiCode: String,
        @Field("code") hideCode: Int
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("eLImg")
    fun eLImg(
        @Field("phone") phone: String,
        @Field("ac") apiCode: String,
        @Field("code") hideCode: Int
    ): Call<ServerRes>

}