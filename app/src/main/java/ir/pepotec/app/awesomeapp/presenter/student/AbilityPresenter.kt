package ir.pepotec.app.awesomeapp.presenter.student

import com.google.gson.Gson
import ir.pepotec.app.awesomeapp.model.ServerRes
import ir.pepotec.app.awesomeapp.model.ServerResConst
import ir.pepotec.app.awesomeapp.model.student.ability.Ability
import ir.pepotec.app.awesomeapp.model.student.ability.AbilityData
import ir.pepotec.app.awesomeapp.model.student.ability.AbilityList
import ir.pepotec.app.awesomeapp.model.user.UserDb
import ir.pepotec.app.awesomeapp.view.uses.App

class AbilityPresenter(private val listener: AbilityResult) : Ability.AbilityResponse {

    interface AbilityResult {
        fun abilityData(ok:Boolean, message:String, data: AbilityData?){}
        fun abilityListData(ok:Boolean, message:String, data: ArrayList<AbilityList>?){}
        fun abilityDeleteResult(ok: Boolean, message: String){}
        fun resultFromAbility(ok: Boolean, message: String){}
    }

    fun addAbility(subject: String, resume: String, description: String) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        Ability(this).addAbility(phone, ac, subject, resume, description)
    }

    fun getAbilityList() {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        Ability(this).getAbilityList(phone, ac)
    }

    fun getAbility(id: String) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        Ability(this).getAbility(id, phone, ac)
    }

    fun editAbility(id: String, subject: String, resume: String, description: String) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        Ability(this)
            .editAbility(id, phone, ac, subject, resume, description)
    }

    fun eyeCloseAbility(id: String) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        Ability(this).eyeCloseAbility(id, phone, ac)
    }

    fun deleteAbility(id: String) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        Ability(this).deleteAbility(id, phone, ac)
    }

    override fun addAbilityRes(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.resultFromAbility(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!")
            ServerResConst.error -> listener.resultFromAbility(
                false,
                "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!"
            )
            ServerResConst.ok -> listener.resultFromAbility(true, "عملیات موفق بود.")
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun getAbilityListRes(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.abilityListData(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!", null)
            ServerResConst.error -> listener.abilityListData(
                false,
                "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!",
                null
            )
            ServerResConst.ok -> {
                val data = ArrayList<AbilityList>()
                val dataArray = res?.data
                for (i in 0..(dataArray?.length() ?: 0)) {
                    val json = (dataArray)?.getJSONObject(i).toString()
                    data.add(Gson().fromJson(json, AbilityList::class.java))
                }
                listener.abilityListData(true, "", data)
            }
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun getAbilityRes(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.abilityData(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!",null)
            ServerResConst.error -> listener.abilityData(
                false,
                "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!",
                null
            )
            ServerResConst.ok -> {
                val json = (res?.data)?.getJSONObject(0).toString()
                val data = Gson().fromJson(json, AbilityData::class.java)
                listener.abilityData(true, "", data)
            }
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun deleteAbilityRes(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.abilityDeleteResult(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!")

            ServerResConst.error -> listener.abilityDeleteResult(
                false,
                "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!"
            )

            ServerResConst.ok -> listener.abilityDeleteResult(true, "با موفقیت حذف شد.")

            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun editAbilityRes(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.resultFromAbility(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!")

            ServerResConst.error -> listener.resultFromAbility(
                false,
                "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!"
            )

            ServerResConst.ok -> listener.resultFromAbility(true, "با موفقیت انجام شد.")

            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun eyeCloseAbilityRes(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.resultFromAbility(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!")

            ServerResConst.error -> listener.resultFromAbility(
                false,
                "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!"
            )

            ServerResConst.ok -> listener.resultFromAbility(true, "با موفقیت انجام شد.")

            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun abilityError(message: String?) {
        listener.resultFromAbility(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!")
    }

}