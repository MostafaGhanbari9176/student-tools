package ir.pepotec.app.awesomeapp.model.blog

import ir.pepotec.app.awesomeapp.model.ServerRes
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface BlogApi {

    @FormUrlEncoded
    @POST("${Blog.baseUrl}add")
    fun add(
        @Field("phone") phone:String,
        @Field("apiCode") apiCode:String,
        @Field("mText") mText:String
    ): Call<ServerRes>

    @Multipart
    @POST("${Blog.baseUrl}addWImg")
    fun addWithImg(
        @Part("phone") phone:RequestBody,
        @Part("apiCode") apiCode:RequestBody,
        @Part("mText") mText:RequestBody,
        @Part file:MultipartBody.Part
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${Blog.baseUrl}img")
    fun getImg(
        @Field("phone") phone:String,
        @Field("apiCode") apiCode:String,
        @Field("imgId") imgId:Int
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("${Blog.baseUrl}getPerLike")
    fun getPerLike(
        @Field("phone") phone:String,
        @Field("apiCode") apiCode:String,
        @Field("num") num:Int,
        @Field("step") step:Int
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${Blog.baseUrl}getAll")
    fun getAll(
        @Field("phone") phone:String,
        @Field("apiCode") apiCode:String,
        @Field("num") num:Int,
        @Field("step") step:Int
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${Blog.baseUrl}changeLike")
    fun changLike(
        @Field("phone") phone:String,
        @Field("apiCode") apiCode:String,
        @Field("mId") mId:Int
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${Blog.baseUrl}report")
    fun report(
        @Field("phone") phone:String,
        @Field("apiCode") apiCode:String,
        @Field("mId") mId:Int,
        @Field("status") status:Int
    ): Call<ServerRes>
}