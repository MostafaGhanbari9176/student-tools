package ir.pepotec.app.awesomeapp.model.student.ability

import ir.pepotec.app.awesomeapp.model.ApiClient
import ir.pepotec.app.awesomeapp.model.ServerRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Ability(private val listener: AbilityResponse) {

    interface AbilityResponse
    {
        fun addAbilityRes(res:ServerRes?)
        fun getAbilityListRes(res:ServerRes?)
        fun getAbilityRes(res:ServerRes?)
        fun deleteAbilityRes(res:ServerRes?)
        fun editAbilityRes(res:ServerRes?)
        fun eyeCloseAbilityRes(res:ServerRes?)
        fun abilityError(message:String?)
    }

    fun addAbility(phone:String, apiCode:String, subject:String, resume:String, description:String)
    {
        val api: AbilityApi = ApiClient.getClient().create(AbilityApi::class.java)
        val req:Call<ServerRes> = api.addAbility(phone, apiCode, subject, resume, description)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.abilityError(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.addAbilityRes(response.body())
            }
        })
    }

    fun getAbilityList(phone:String, apiCode:String)
    {
        val api: AbilityApi = ApiClient.getClient().create(AbilityApi::class.java)
        val req:Call<ServerRes> = api.getAbilityList(phone, apiCode)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.abilityError(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.getAbilityListRes(response.body())
            }
        })
    }

    fun getAbility(id:String, phone:String, apiCode:String)
    {
        val api: AbilityApi = ApiClient.getClient().create(AbilityApi::class.java)
        val req:Call<ServerRes> = api.getAbility(id, phone, apiCode)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.abilityError(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.getAbilityRes(response.body())
            }
        })
    }

    fun editAbility(id:String, phone:String, apiCode:String, subject:String, resume:String, description:String)
    {
        val api: AbilityApi = ApiClient.getClient().create(AbilityApi::class.java)
        val req:Call<ServerRes> = api.editAbility(id, phone, apiCode, subject, resume, description)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.abilityError(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.editAbilityRes(response.body())
            }
        })
    }

    fun eyeCloseAbility(id:String, phone:String, apiCode:String)
    {
        val api: AbilityApi = ApiClient.getClient().create(AbilityApi::class.java)
        val req:Call<ServerRes> = api.eyeCloseAbility(id, phone, apiCode)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.abilityError(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.eyeCloseAbilityRes(response.body())
            }
        })
    }

    fun deleteAbility(id:String, phone:String, apiCode:String)
    {
        val api: AbilityApi = ApiClient.getClient().create(AbilityApi::class.java)
        val req:Call<ServerRes> = api.deleteAbility(id, phone, apiCode)

        req.enqueue(object : Callback<ServerRes> {
            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                listener.abilityError(t.message)
            }

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                listener.deleteAbilityRes(response.body())
            }
        })
    }

}