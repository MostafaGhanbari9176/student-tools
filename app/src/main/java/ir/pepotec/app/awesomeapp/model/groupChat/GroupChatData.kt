package ir.pepotec.app.awesomeapp.model.groupChat
import androidx.annotation.Keep

@Keep
data class GroupChatData (
    val g_id:Int,
    val g_name:String,
    val admin:Boolean,
    val g_info:String,
    val join_mode:Int,
    val invite_mode:Int,
    val c_date:String,
    val c_time:String
)