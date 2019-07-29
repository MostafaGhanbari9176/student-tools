package ir.pepotec.app.awesomeapp.model.student.workSample

import ir.pepotec.app.awesomeapp.model.ServerRes
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface WorkSampleApi {

    @FormUrlEncoded
    @Multipart
    @POST("addWorkSample")
    fun addWorkSample(
        @Field("phone") phone: String,
        @Field("ac") apiCode: String,
        @Field("subject") subject: String,
        @Field("description") description: String,
        @Part files: ArrayList<MultipartBody.Part>
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("getWorkSampleList")
    fun getWorkSampleList(
        @Field("phone") phone: String,
        @Field("ac") apiCode: String,
        @Field("abilityId") abilityId:String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("getWorkSample")
    fun getWorkSample(
        @Field("id") id: String,
        @Field("phone") phone: String,
        @Field("ac") apiCode: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @Multipart
    @POST("editWorkSample")
    fun editWorkSample(
        @Field("id") id: String,
        @Field("phone") phone: String,
        @Field("ac") apiCode: String,
        @Field("subject") subject: String,
        @Field("description") description: String,
        @Part files: ArrayList<MultipartBody.Part>
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("eyeCloseWorkSample")
    fun eyeCloseWorkSample(
        @Field("id") id: String,
        @Field("phone") phone: String,
        @Field("ac") apiCode: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("deleteWorkSample")
    fun deleteWorkSample(
        @Field("id") id: String,
        @Field("phone") phone: String,
        @Field("ac") apiCode: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("workSampleImg")
    fun workSampleImg(
        @Field("imgId") imgId: String,
        @Field("phone") phone:String,
        @Field("ac") apiCode:String
    ):Call<ResponseBody>

}