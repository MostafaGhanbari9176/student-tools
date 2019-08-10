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
    val work_sample_id: String,
    val subject: String,
    val description: String,
    val add_date: String,
    val status: Int,
    val seen_num: Int,
    val like_num: Int,
    val liked: Boolean,
    val img_num: Int
)

data class workSampleList(
    val work_sample_id: Int,
    val seen_num:Int,
    val like_num:Int
)