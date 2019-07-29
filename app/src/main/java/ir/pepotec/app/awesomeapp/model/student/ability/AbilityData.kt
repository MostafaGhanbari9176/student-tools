package ir.pepotec.app.awesomeapp.model.student.ability

/*
*   status
*     {
*         0 -> darEntezar
*         1 -> montasherShode
*         2 -> radShode
*         3 -> adamNamayesh
*         4 -> delete
*     }
* */
data class AbilityData(
    val id: String,
    val subject: String,
    val resume: String,
    val description: String,
    val date: String,
    val status: Int
)

data class AbilityList(
    val id: String,
    val subject: String
)

