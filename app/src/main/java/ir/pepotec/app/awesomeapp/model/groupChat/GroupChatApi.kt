package ir.pepotec.app.awesomeapp.model.groupChat

import ir.pepotec.app.awesomeapp.model.ServerRes
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface GroupChatApi {

    @FormUrlEncoded
    @POST(GroupChat.baseUrl+"create")
    fun add(
    @Field("phone") phone:String,
    @Field("apiCode") apiCode:String,
    @Field("groupName") name:String,
    @Field("info") info:String,
    @Field("joinMode") joinMode:Int,
    @Field("inviteMode") inviteMode:Int
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST(GroupChat.baseUrl+"get")
    fun getData(
        @Field("phone") phone:String,
        @Field("apiCode") apiCode:String,
        @Field("groupId") groupId:Int
    ):Call<ServerRes>

    @FormUrlEncoded
    @POST(GroupChat.baseUrl+"memberList")
    fun memberList(
        @Field("phone") phone:String,
        @Field("apiCode") apiCode:String,
        @Field("groupId") groupId:Int
    ):Call<ServerRes>

    @FormUrlEncoded
    @POST(GroupChat.baseUrl+"left")
    fun left(
        @Field("phone") phone:String,
        @Field("apiCode") apiCode:String,
        @Field("groupId") groupId:Int
    ):Call<ServerRes>

    @FormUrlEncoded
    @POST(GroupChat.baseUrl+"removeMember")
    fun removeMember(
        @Field("phone") phone:String,
        @Field("apiCode") apiCode:String,
        @Field("groupId") groupId:Int,
        @Field("otherId") user_id:Int
    ):Call<ServerRes>

    @Multipart
    @POST(GroupChat.baseUrl+"upImg")
    fun uploadImage(
        @Part("phone") phone:RequestBody,
        @Part("apiCode") apiCode:RequestBody,
        @Part("groupId") groupId:RequestBody,
        @Part file:MultipartBody.Part
    ):Call<ServerRes>

}