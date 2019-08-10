package ir.pepotec.app.awesomeapp.model.user

import ir.pepotec.app.awesomeapp.model.Pref

class UserDb {

    val phone = "userPhone"
    val apiCode = "userAc"
    val kind = "userKind"

    fun saveUserData(data: UserData) {
        Pref().apply {
            saveStringValue(phone, data.phone)
            saveStringValue(apiCode, data.api_code)
            saveIntegerValue(kind, data.kind)
        }
    }

    fun getUserPhone(): String = Pref().getStringValue(phone, "")

    fun getUserApiCode():String = Pref().getStringValue(apiCode, "")

    fun removeApiCode() {
        Pref().removeValue(apiCode)
    }

    fun savePhone(phone: String) {
        Pref().saveStringValue(this@UserDb.phone, phone)
    }

    fun saveApiCode(apiCode: String) {
        Pref().saveStringValue(this@UserDb.apiCode, apiCode)
    }

    fun removeData() {
        Pref().apply {
            removeValue(phone)
            removeValue(apiCode)
            removeValue(kind)
        }
    }
}