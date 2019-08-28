package ir.pepotec.app.awesomeapp.model.student.ability

import ir.pepotec.app.awesomeapp.model.ApiClient
import ir.pepotec.app.awesomeapp.model.ServerRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Ability(private val listener: AbilityResponse) : Callback<ServerRes> {

    companion object {
        const val baseUrl = "student/ability/index.php/"
    }

    private val api: AbilityApi = ApiClient.getClient().create(AbilityApi::class.java)

    interface AbilityResponse {
        fun abilityRes(res: ServerRes?){}
    }

    fun addAbility(phone: String, apiCode: String, subject: String, resume: String, description: String) {
        api.addAbility(phone, apiCode, subject, resume, description).apply { enqueue(this@Ability) }
    }

    fun getMyList(phone: String, apiCode: String) {
        api.getMyList(phone, apiCode).apply { enqueue(this@Ability) }
    }

    fun getSingle(id: Int, phone: String, apiCode: String, itsMy: Boolean) {

        val req: Call<ServerRes> = when (itsMy) {
            true -> api.getMySingle(id, phone, apiCode)
            else -> api.getOtherSingle(id, phone, apiCode)
        }
        req.enqueue(this)
    }

    fun increaseSeen(id: Int, phone: String, apiCode: String) {
        api.increaseSeen(id, phone, apiCode).apply { enqueue(this@Ability) }
    }

    fun search(phone: String, ac: String, key: String, num: Int, step: Int) {
        api.search(phone, ac, key, num, step).apply { enqueue(this@Ability) }
    }

    fun editAbility(id: Int, phone: String, apiCode: String, subject: String, resume: String, description: String) {
        api.editAbility(id, phone, apiCode, subject, resume, description).apply { enqueue(this@Ability) }
    }

    fun deleteAbility(id: Int, phone: String, apiCode: String) {
        api.deleteAbility(id, phone, apiCode).apply { enqueue(this@Ability) }
    }

    override fun onFailure(call: Call<ServerRes>, t: Throwable) {
        listener.abilityRes(null)
    }

    override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
        listener.abilityRes(response.body())
    }

}