package ir.pepotec.app.awesomeapp.model.student.profile

import ir.pepotec.app.awesomeapp.model.Pref

class StudentProfileDb {

    private val sId = "chat_subject"
    private val name = "studentName"
    private val logSign = "is_login"
    var isLogIn: Boolean
        set(value) {
            Pref().saveBollValue(logSign, value)
        }
        get() = Pref().getBollValue(logSign, false)

    fun saveData(data: StudentProfileData) {
        Pref().apply {
            saveStringValue(sId, data.s_id)
            saveStringValue(name, data.user_name)
        }
    }

    fun removeData()
    {
        Pref().apply {
            removeValue(sId)
            removeValue(name)
            removeValue(logSign)
        }
    }

    fun getStudentName(): String = Pref().getStringValue(name, "")

    fun getStudentId(): String = Pref().getStringValue(sId, "")

    fun saveLastSeen(message: String, chatId: Int) {
        Pref().saveStringValue("last$chatId", message)
    }

    fun getLastSeen(chatId: Int): String = Pref().getStringValue("last$chatId","...")


}