package ir.pepotec.app.awesomeapp.model.student.profile

import ir.pepotec.app.awesomeapp.model.Pref

class StudentProfileDb {

    val sId = "s_id"
    val name = "studentName"

    fun saveData(data:StudentProfileData)
    {
        Pref().apply {
            saveStringValue(sId, data.s_id)
            saveStringValue(name, data.user_name)
        }
    }

    fun getStudentName():String = Pref().getStringValue(name, "")

    fun getStudentId():String = Pref().getStringValue(sId, "")

}