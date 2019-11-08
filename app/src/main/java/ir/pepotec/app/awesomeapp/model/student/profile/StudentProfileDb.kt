package ir.pepotec.app.awesomeapp.model.student.profile

import ir.pepotec.app.awesomeapp.model.Pref

class StudentProfileDb {

    private val sId = "chat_subject"
    private val name = "studentName"
    private val email = "email"
    private val fieldName = "fieldName"
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
            saveStringValue(email, data.email)
            saveStringValue(fieldName, data.fieldName)
        }
    }

    fun removeData()
    {
        Pref().apply {
            removeValue(sId)
            removeValue(name)
            removeValue(logSign)
            removeValue(email)
            removeValue(fieldName)
        }
    }

    fun getStudentName(): String = Pref().getStringValue(name, "")

    fun getStudentEamil(): String = Pref().getStringValue(email, "")

    fun getStudentFieldName(): String = Pref().getStringValue(fieldName, "")

    fun getStudentId(): String = Pref().getStringValue(sId, "")

    fun saveLastSeen(message: String, chatId: Int) {
        Pref().saveStringValue("last$chatId", message)
    }

    fun getLastSeen(chatId: Int): String = Pref().getStringValue("last$chatId","...")


}