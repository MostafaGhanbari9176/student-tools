package ir.pepotec.app.awesomeapp.model.student.workSample

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
data class WorkSampleData(
    val id: String,
    val subject: String,
    val description: String,
    val date: String,
    val state: Int,
    val eyeNumber: Int,
    val likeNumber: Int,
    val imgId: ArrayList<String>
)

data class workSampleList(
    val id: String,
    val imgId: String,
    val eyeNumber:Int,
    val likeNumber:Int
)