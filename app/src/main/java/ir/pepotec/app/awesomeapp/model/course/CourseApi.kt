package ir.pepotec.app.awesomeapp.model.course

import ir.pepotec.app.awesomeapp.model.ServerRes
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CourseApi {

    @FormUrlEncoded
    @POST(Course.baseUrl+"getFirstData")
    fun getFirstData(
        @Field("phone") phone:String,
        @Field("apiCode") apiCode:String
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST(Course.baseUrl+"get")
    fun get(
        @Field("phone") phone:String,
        @Field("apiCode") apiCode:String,
        @Field("courseId") newsId:Int
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST(Course.baseUrl+"getListData")
    fun getListData(
        @Field("phone") phone:String,
        @Field("apiCode") apiCode:String,
        @Field("groupId") groupId:Int,
        @Field("num") num:Int,
        @Field("step") step:Int
    ): Call<ServerRes>

    @FormUrlEncoded
    @POST(Course.baseUrl+"getSpecial")
    fun getSpecial(
        @Field("phone") phone:String,
        @Field("apiCode") apiCode:String
    ): Call<ServerRes>

}