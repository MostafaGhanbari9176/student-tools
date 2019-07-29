package ir.pepotec.app.awesomeapp.model.student.profile

import ir.pepotec.app.awesomeapp.model.Pref

class StudentProfileDb {

    val sId = "studentId"
    val name = "studentName"

    fun saveData(data:StudentProfileData)
    {
        Pref().apply {
            saveStringValue(sId, data.studentId)
            saveStringValue(name, data.name)
        }
    }

    fun getStudentName():String = Pref().getStringValue(name, "")

    fun getStudentId():String = Pref().getStringValue(sId, "")

}