package ir.pepotec.app.awesomeapp.model.student.chat

import ir.pepotec.app.awesomeapp.model.ServerRes
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ChatApi {

    @FormUrlEncoded
    @POST("${Chat.baseUrl}getLastMessage")
    fun getLastMessage(
        @Field("phone") phone:String,
        @Field("apiCode") apiCode:String,
        @Field("otherId") userId:Int
    ):Call<ServerRes>

    @FormUrlEncoded
    @POST("${Chat.baseUrl}getOldMessage")
    fun getOldMessage(
        @Field("phone") phone:String,
        @Field("apiCode") apiCode:String,
        @Field("otherId") userId:Int,
        @Field("lastId") lastId:Int
    ):Call<ServerRes>

    @FormUrlEncoded
    @POST("${Chat.baseUrl}getNewMessage")
    fun getNewMessage(
        @Field("phone") phone:String,
        @Field("apiCode") apiCode:String,
        @Field("otherId") userId:Int,
        @Field("lastId") lastId:Int
    ):Call<ServerRes>

    @FormUrlEncoded
    @POST("${Chat.baseUrl}sendMessage")
    fun sendMessage(
        @Field("phone") phone:String,
        @Field("apiCode") apiCode:String,
        @Field("otherId") userId:Int,
        @Field("message[]") messageList:ArrayList<String>
    ):Call<ServerRes>

    @Multipart
    @POST("${Chat.baseUrl}sendFileMessage")
    fun sendFileMessage(
        @Part("phone") phone:RequestBody,
        @Part("apiCode") apiCode:RequestBody,
        @Part("message") message:RequestBody,
        @Part("otherId") userId:RequestBody,
        @Part("pathId") pathId:RequestBody,
        @Part file:MultipartBody.Part
    ):Call<ServerRes>

    @FormUrlEncoded
    @POST("${Chat.baseUrl}seen")
    fun updateSeen(
        @Field("phone") phone:String,
        @Field("apiCode") apiCode:String,
        @Field("otherId") otherId:Int
    ):Call<ServerRes>

}