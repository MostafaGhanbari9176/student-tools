package ir.pepotec.app.awesomeapp.model.student.ability

import ir.pepotec.app.awesomeapp.model.ServerRes
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AbilityApi {

    @FormUrlEncoded
    @POST("${Ability.baseUrl}addAbility")
    fun addAbility(
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String,
        @Field("subject") subject: String,
        @Field("resume") resume: String,
        @Field("description") description: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${Ability.baseUrl}getMyList")
    fun getMyList(
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${Ability.baseUrl}getMySingle")
    fun getMySingle(
        @Field("abilityId") id: Int,
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${Ability.baseUrl}seen")
    fun increaseSeen(
        @Field("abilityId") id: Int,
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${Ability.baseUrl}getSingle")
    fun getOtherSingle(
        @Field("abilityId") id: Int,
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${Ability.baseUrl}editAbility")
    fun editAbility(
        @Field("abilityId") id: Int,
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String,
        @Field("subject") subject: String,
        @Field("resume") resume: String,
        @Field("description") description: String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST("${Ability.baseUrl}delete")
    fun deleteAbility(
        @Field("abilityId") id: Int,
        @Field("phone") phone: String,
        @Field("apiCode") apiCode: String
    ): Call<ServerRes>

}