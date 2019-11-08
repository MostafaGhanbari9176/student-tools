package ir.pepotec.app.awesomeapp.model.chat

data class ChatMessageData(
    val m_id:Int,
    val user_id: Int,
    //val user_sub:String,
    var its_my:Boolean,
    val send_date:String,
    val send_time:String,
    val m_text:String,
    var status:Int,
    val file_id:Int,
    var path_id:Int,
    var fPath:String,
    var i_id:Int,
    var animating:Boolean = false
)

data class ChatListData(
    val chat_id:Int,
    val kind_id:String,
    val chat_subject:String,
    val message:String
)

data class ChatReq(
    val chat_id:Int,
    val kind_id:String,
    val lastId:Int
)

data class ChatRes(
    val chat_id:Int,
    val kind_id:String,
    val lastSeenId:Int,
    val messageList:ArrayList<ChatMessageData>
)