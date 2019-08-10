package ir.pepotec.app.awesomeapp.model.student.workSample

import ir.pepotec.app.awesomeapp.model.ServerRes
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface WorkSampleApi {

    @Multipart
    @POST("${WorkSample.baseUrl}add")
    fun addWorkSample(
        @Part("phone") phone: RequestBody,
        @Part("apiCode") apiCode: RequestBody,
        @Part("abilityId") abilityId: RequestBody,
        @Part("subject") subject: RequestBody,
        @Part("description") description: RequestBody,
        @Part files: ArrayList<MultipartBody.Part>
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${WorkSample.baseUrl}getMyList")
    fun getMyWorkSampleList(
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String,
        @Field("abilityId") abilityId:Int
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${WorkSample.baseUrl}getList")
    fun getOtherWorkSampleList(
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String,
        @Field("abilityId") abilityId:Int
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${WorkSample.baseUrl}getMySingle")
    fun getMyWorkSample(
        @Field("workSampleId") id: Int,
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${WorkSample.baseUrl}getSingle")
    fun getOtherWorkSample(
        @Field("workSampleId") id: Int,
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${WorkSample.baseUrl}seen")
    fun increaseSeen(
        @Field("workSampleId") id: Int,
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String
    ): Call<ServerRes>

    @Multipart
    @POST("${WorkSample.baseUrl}edit")
    fun editWorkSample(
        @Part("workSampleId") id: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("apiCode") apiCode: RequestBody,
        @Part("subject") subject: RequestBody,
        @Part("description") description: RequestBody,
        @Part files: ArrayList<MultipartBody.Part>
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${WorkSample.baseUrl}delete")
    fun deleteWorkSample(
        @Field("workSampleId") id: Int,
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${WorkSample.baseUrl}changeLike")
    fun likeWorkSample(
        @Field("workSampleId") id: Int,
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${WorkSample.baseUrl}img")
    fun workSampleImg(
        @Field("imgId") imgId: String,
        @Field("phone") phone:String,
        @Field("apiCode") apiCode:String
    ):Call<ResponseBody>

}