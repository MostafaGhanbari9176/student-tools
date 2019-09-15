package ir.pepotec.app.awesomeapp.model.invite

import ir.pepotec.app.awesomeapp.model.ServerRes
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface InviteApi {

    @FormUrlEncoded
    @POST(Invite.baseUrl+"inviteMessage")
    fun add(
        @Field("phone") phone:String,
        @Field("apiCode") apiCode:String,
        @Field("otherId") userId:Int,
        @Field("groupId") groupId:Int
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST(Invite.baseUrl+"accept")
    fun accept(
        @Field("phone") phone:String,
        @Field("apiCode") apiCode:String,
        @Field("inviteId") inviteId:Int
    ): Call<ServerRes>

}