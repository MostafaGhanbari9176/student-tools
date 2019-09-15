package ir.pepotec.app.awesomeapp.model.chat

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
        @Field("chatId") chat_id:Int,
        @Field("kindId") kind_id:String
    ):Call<ServerRes>

    @FormUrlEncoded
    @POST("${Chat.baseUrl}getOldMessage")
    fun getOldMessage(
        @Field("phone") phone:String,
        @Field("apiCode") apiCode:String,
        @Field("chatId") chat_id:Int,
        @Field("kindId") kind_id:String,
        @Field("lastId") lastId:Int
    ):Call<ServerRes>

    @FormUrlEncoded
    @POST("${Chat.baseUrl}getNewMessage")
    fun getNewMessage(
        @Field("phone") phone:String,
        @Field("apiCode") apiCode:String,
        @Field("kindId") kind_id:String,
        @Field("reqData[]") reqData:ArrayList<String>
    ):Call<ServerRes>

    @FormUrlEncoded
    @POST("${Chat.baseUrl}sendMessage")
    fun sendMessage(
        @Field("phone") phone:String,
        @Field("apiCode") apiCode:String,
        @Field("chatId") chat_id:Int,
        @Field("kindId") kind_id:String,
        @Field("message[]") messageList:ArrayList<String>
    ):Call<ServerRes>

    @Multipart
    @POST("${Chat.baseUrl}sendFileMessage")
    fun sendFileMessage(
        @Part("phone") phone:RequestBody,
        @Part("apiCode") apiCode:RequestBody,
        @Part("chatId") chat_id:RequestBody,
        @Part("kindId") kind_id:RequestBody,
        @Part("message") message:RequestBody,
        @Part("userId") user_id:RequestBody,
        @Part("pathId") pathId:RequestBody,
        @Part file:MultipartBody.Part
    ):Call<ServerRes>

    @FormUrlEncoded
    @POST("${Chat.baseUrl}seen")
    fun updateSeen(
        @Field("phone") phone:String,
        @Field("apiCode") apiCode:String,
        @Field("chatId") chat_id:Int,
        @Field("kindId") kind_id:String
    ):Call<ServerRes>

}