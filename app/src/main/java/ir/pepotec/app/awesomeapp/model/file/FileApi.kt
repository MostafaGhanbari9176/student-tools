package ir.pepotec.app.awesomeapp.model.file

import ir.pepotec.app.awesomeapp.model.ServerRes
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface FileApi {

    @FormUrlEncoded
    @POST("${FileModel.baseUrl}download")
    fun downloadFile(
        @Field("fileId") fileId: Int,
        @Field("phone") phone:String,
        @Field("apiCode") apiCode:String
    ):Call<ResponseBody>

    @FormUrlEncoded
    @POST("${FileModel.baseUrl}getType")
    fun getType(
        @Field("fileId") fileId: Int,
        @Field("phone") phone:String,
        @Field("apiCode") apiCode:String
    ):Call<ServerRes>

}