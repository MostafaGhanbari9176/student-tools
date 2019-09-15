package ir.pepotec.app.awesomeapp.model.student.profile

data class StudentProfileData
    (
    val s_id: String,
    val email: String,
    val about_me: String,
    val phone: String,
    val last_date: String,
    val last_time: String,
    val user_id: Int,
    val its_friend: Boolean,
    val user_name: String,
    var account: Boolean = false,
    val owner:Boolean = false
)

class StudentEyeLevel {
    companion object {
        val getFromServer: Int = -1
        val allUser: Int = 0
        val justFriends: Int = 1
        val nobody = 2
    }
}