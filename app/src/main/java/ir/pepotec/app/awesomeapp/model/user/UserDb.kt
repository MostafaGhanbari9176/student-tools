package ir.pepotec.app.awesomeapp.model.user

import ir.pepotec.app.awesomeapp.model.Pref

class UserDb {

    val phone = "userPhone"
    val apiCode = "userAc"
    val kind = "userKind"

    fun saveUserData(data: UserData)
    {
        Pref().apply {
            saveStringValue(phone, data.phone)
            saveStringValue(apiCode, data.apiCode)
            saveIntegerValue(kind, data.kind)
        }
    }

    fun removeData() {
        Pref().apply {
              removeValue(phone)
              removeValue(apiCode)
              removeValue(kind)
            }
    }
}