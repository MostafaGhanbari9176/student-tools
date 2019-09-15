package ir.pepotec.app.awesomeapp.model.student.profile

import ir.pepotec.app.awesomeapp.model.Pref

class StudentProfileDb {

    val sId = "chat_subject"
    val name = "studentName"
    var isLogIn: Boolean
        set(value) {
            Pref().saveBollValue("is_login", value)
        }
        get() = Pref().getBollValue("is_login", false)

    fun saveData(data: StudentProfileData) {
        Pref().apply {
            saveStringValue(sId, data.s_id)
            saveStringValue(name, data.user_name)
        }
    }

    fun getStudentName(): String = Pref().getStringValue(name, "")

    fun getStudentId(): String = Pref().getStringValue(sId, "")

}