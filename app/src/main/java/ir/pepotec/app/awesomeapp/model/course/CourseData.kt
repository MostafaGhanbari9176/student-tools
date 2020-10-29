package ir.pepotec.app.awesomeapp.model.course
import androidx.annotation.Keep

@Keep
data class CourseData(
    val c_id:Int,
    val c_name:String,
    val c_text:String,
    val c_date:String,
    val group_id:Int,
    val field_id:Int,
    val seen:Int,
    val special:Int,
    val owner_name:String,
    val phone:String,
    val email:String,
    val website:String,
    val social:String

)

@Keep
data class CourseListData(
    val g_id:Int,
    val g_name:String,
    val courseData:ArrayList<CourseData>
)
