package ir.pepotec.app.awesomeapp.presenter.student

import com.google.gson.Gson
import ir.pepotec.app.awesomeapp.model.ServerRes
import ir.pepotec.app.awesomeapp.model.student.ability.Ability
import ir.pepotec.app.awesomeapp.model.student.ability.AbilityData
import ir.pepotec.app.awesomeapp.model.student.ability.AbilityList
import ir.pepotec.app.awesomeapp.model.user.UserDb
import ir.pepotec.app.awesomeapp.view.uses.AF

class AbilityPresenter(private val listener: AbilityResult) : Ability.AbilityResponse {

    interface AbilityResult {
        fun abilityData(ok: Boolean, message: String, data: AbilityData?) {}
        fun abilityListData(ok: Boolean, message: String, data: ArrayList<AbilityList>?) {}
        fun resultFromAbility(ok: Boolean, message: String) {}
    }

    fun addAbility(subject: String, resume: String, description: String) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        Ability(this).addAbility(phone, ac, subject, resume, description)
    }

    fun getMyList() {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        Ability(this).getMyList(phone, ac)
    }

    fun getSingle(id: Int, itsMy: Boolean) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        Ability(this).getSingle(id, phone, ac, itsMy)
    }

    fun increaseSeen(id: Int) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        Ability(this).increaseSeen(id, phone, ac)
    }

    fun editAbility(id: Int, subject: String, resume: String, description: String) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        Ability(this)
            .editAbility(id, phone, ac, subject, resume, description)
    }

    fun deleteAbility(id: Int) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        Ability(this).deleteAbility(id, phone, ac)
    }

    override fun addAbilityRes(res: ServerRes?) {
        listener.resultFromAbility(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1))
    }

    override fun getAbilityListRes(res: ServerRes?) {
        val data = ArrayList<AbilityList>()
        res?.let {
            for (o in it.data)
                data.add(Gson().fromJson(o, AbilityList::class.java))
        }
        listener.abilityListData(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1), data)

    }

    override fun getAbilityRes(res: ServerRes?) {
        var data: AbilityData? = null
        res?.let {
            data = Gson().fromJson(it.data[0], AbilityData::class.java)
        }
        listener.abilityData(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1), data)
    }

    override fun changeStatusRes(res: ServerRes?) {
        listener.resultFromAbility(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1))
    }

    override fun editAbilityRes(res: ServerRes?) {
        listener.resultFromAbility(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1))
    }

}