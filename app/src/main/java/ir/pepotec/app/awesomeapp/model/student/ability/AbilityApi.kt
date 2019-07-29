package ir.pepotec.app.awesomeapp.model.student.ability

import ir.pepotec.app.awesomeapp.model.ServerRes
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AbilityApi {

    @FormUrlEncoded
    @POST("addAbility")
    fun addAbility(
        @Field("phone") phone: String,
        @Field("ac") apiCode: String,
        @Field("subject") subject: String,
        @Field("resume") resume: String,
        @Field("description") description: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("getAbilityList")
    fun getAbilityList(
        @Field("phone") phone: String,
        @Field("ac") apiCode: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("getAbility")
    fun getAbility(
        @Field("id") id: String,
        @Field("phone") phone: String,
        @Field("ac") apiCode: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("editAbility")
    fun editAbility(
        @Field("id") id: String,
        @Field("phone") phone: String,
        @Field("ac") apiCode: String,
        @Field("subject") subject: String,
        @Field("resume") resume: String,
        @Field("description") description: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("eyeCloseAbility")
    fun eyeCloseAbility(
        @Field("id") id: String,
        @Field("phone") phone: String,
        @Field("ac") apiCode: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("deleteAbility")
    fun deleteAbility(
        @Field("id") id: String,
        @Field("phone") phone: String,
        @Field("ac") apiCode: String
    ): Call<ServerRes>

}