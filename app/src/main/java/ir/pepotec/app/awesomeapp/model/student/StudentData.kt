package ir.pepotec.app.awesomeapp.model.student

data class StudentProfile(val studentId: String, val name: String, val imgId: String)

data class Ability(
    val id: String,
    val subject: String,
    val resume: String,
    val description: String,
    val date: String,
    val stateSubject: String
)

data class AbilityList(
    val id: Int,
    val subject: String
)

data class WorkSample(
    val id: String,
    val subject: String,
    val description: String,
    val date: String,
    val seenNumber: Int,
    val likeNumber: Int,
    val coverImg: String,
    val img: Array<String>
)

data class WrokSampleList(
    val id: Int,
    val coverImg: String,
    val seenNumber: Int,
    val likeNumber: Int
)
