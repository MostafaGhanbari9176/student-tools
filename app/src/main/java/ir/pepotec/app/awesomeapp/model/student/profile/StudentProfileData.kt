package ir.pepotec.app.awesomeapp.model.student.profile

data class StudentProfileData
    (
    val studentId: String,
    val name:String
    )
class StudentEyeLevel
{
    companion object
    {
        val getFromServer:Int = -1
        val allUser:Int = 0
        val justFriends:Int = 1
        val nobody = 2
    }
}