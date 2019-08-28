package ir.pepotec.app.awesomeapp.presenter.student

import com.google.gson.Gson
import ir.pepotec.app.awesomeapp.model.ServerRes
import ir.pepotec.app.awesomeapp.model.student.ability.Ability
import ir.pepotec.app.awesomeapp.model.student.ability.AbilityData
import ir.pepotec.app.awesomeapp.model.student.ability.AbilityList
import ir.pepotec.app.awesomeapp.model.student.profile.StudentProfile
import ir.pepotec.app.awesomeapp.model.user.UserDb
import ir.pepotec.app.awesomeapp.view.uses.AF

class AbilityPresenter(private val listener: AbilityResult) {

    interface AbilityResult {
        fun abilityData(ok: Boolean, message: String, data: AbilityData?) {}
        fun abilityListData(ok: Boolean, message: String, data: ArrayList<AbilityList>?) {}
        fun searchData(ok: Boolean, message: String, data: ArrayList<AbilityData>?) {}
        fun resultFromAbility(ok: Boolean, message: String) {}
    }

    fun addAbility(subject: String, resume: String, description: String) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        Ability(object:Ability.AbilityResponse{
            override fun abilityRes(res: ServerRes?) {
                listener.resultFromAbility(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1))
            }
        }).addAbility(phone, ac, subject, resume, description)
    }

    fun search(key: String, num: Int, step: Int) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        Ability(object:Ability.AbilityResponse{
            override fun abilityRes(res: ServerRes?) {
                val data = ArrayList<AbilityData>()
                res?.let {
                    for (o in it.data)
                        data.add(Gson().fromJson(o, AbilityData::class.java))
                }
                listener.searchData(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1), data)
            }
        }).search(phone, ac, key, num, step)
    }

    fun getMyList() {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        Ability(object:Ability.AbilityResponse{
            override fun abilityRes(res: ServerRes?) {
                val data = ArrayList<AbilityList>()
                res?.let {
                    for (o in it.data)
                        data.add(Gson().fromJson(o, AbilityList::class.java))
                }
                listener.abilityListData(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1), data)

            }
        }).getMyList(phone, ac)
    }

    fun getSingle(id: Int, itsMy: Boolean) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        Ability(object:Ability.AbilityResponse{
            override fun abilityRes(res: ServerRes?) {
                var data: AbilityData? = null
                res?.let {
                    data = Gson().fromJson(it.data[0], AbilityData::class.java)
                }
                listener.abilityData(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1), data)
            }
        }).getSingle(id, phone, ac, itsMy)
    }

    fun increaseSeen(id: Int) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        Ability(object:Ability.AbilityResponse{

        }).increaseSeen(id, phone, ac)
    }

    fun editAbility(id: Int, subject: String, resume: String, description: String) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        Ability(object :Ability.AbilityResponse{
            override fun abilityRes(res: ServerRes?) {
                listener.resultFromAbility(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1))
            }
        }).editAbility(id, phone, ac, subject, resume, description)
    }

    fun deleteAbility(id: Int) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        Ability(object:Ability.AbilityResponse{
            override fun abilityRes(res: ServerRes?) {
                listener.resultFromAbility(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1))
            }
        }).deleteAbility(id, phone, ac)
    }

}