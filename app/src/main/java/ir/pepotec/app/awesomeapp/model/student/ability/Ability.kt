package ir.pepotec.app.awesomeapp.model.student.ability

import ir.pepotec.app.awesomeapp.model.ApiClient
import ir.pepotec.app.awesomeapp.model.ServerRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Ability(private val listener: AbilityResponse) {

    companion object{
       const val baseUrl = "student/ability/index.php/"
    }

    interface AbilityResponse
    {
        fun addAbilityRes(res:ServerRes?)
        fun getAbilityListRes(res:ServerRes?)
        fun getAbilityRes(res:ServerRes?)
        fun editAbilityRes(res:ServerRes?)
        fun changeStatusRes(res:ServerRes?)
    }

    fun addAbility(phone:String, apiCode:String, subject:String, resume:String, description:String)
    {
        val api: AbilityApi = ApiClient.getClient().create(AbilityApi::class.java)
        val req:Call<ServerRes> = api.addAbility(phone, apiCode, subject, resume, description)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.addAbilityRes(null)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.addAbilityRes(response.body())
            }
        })
    }

    fun getMyList(phone:String, apiCode:String)
    {
        val api: AbilityApi = ApiClient.getClient().create(AbilityApi::class.java)
        val req:Call<ServerRes> = api.getMyList(phone, apiCode)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.getAbilityListRes(null)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.getAbilityListRes(response.body())
            }
        })
    }

    fun getSingle(id:Int, phone:String, apiCode:String, itsMy:Boolean)
    {
        val api: AbilityApi = ApiClient.getClient().create(AbilityApi::class.java)
        val req:Call<ServerRes> = when(itsMy) {
            true -> api.getMySingle(id, phone, apiCode)
            else -> api.getOtherSingle(id, phone, apiCode)
        }
                    req.enqueue(object : Callback<ServerRes> {
                override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                    listener.getAbilityRes(null)
                }

                override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                    listener.getAbilityRes(response.body())
                }
            })
        }

    fun increaseSeen(id:Int, phone:String, apiCode:String)
    {
        val api: AbilityApi = ApiClient.getClient().create(AbilityApi::class.java)
        val req:Call<ServerRes> = api.increaseSeen(id, phone, apiCode)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {

            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {

            }
        })
    }


    fun editAbility(id:Int, phone:String, apiCode:String, subject:String, resume:String, description:String)
    {
        val api: AbilityApi = ApiClient.getClient().create(AbilityApi::class.java)
        val req:Call<ServerRes> = api.editAbility(id, phone, apiCode, subject, resume, description)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.editAbilityRes(null)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.editAbilityRes(response.body())
            }
        })
    }

    fun deleteAbility(id:Int, phone:String, apiCode:String)
    {
        val api: AbilityApi = ApiClient.getClient().create(AbilityApi::class.java)
        val req:Call<ServerRes> = api.deleteAbility(id, phone, apiCode)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.changeStatusRes(null)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.changeStatusRes(response.body())
            }
        })
    }

}