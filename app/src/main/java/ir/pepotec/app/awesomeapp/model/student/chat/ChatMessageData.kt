package ir.pepotec.app.awesomeapp.model.student.chat

data class ChatMessageData(
    val m_id:Int,
    val user_id: Int,
    var its_my:Boolean,
    val send_date:String,
    val send_time:String,
    val m_text:String,
    var status:Int,
    val file_id:Int,
    var path_id:Int,
    var fPath:String,
    var animating:Boolean = false
)

data class ChatListData(
    val user_id:Int,
    val s_id:String,
    val message:String
)