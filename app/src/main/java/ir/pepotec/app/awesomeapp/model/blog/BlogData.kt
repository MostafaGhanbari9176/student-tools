package ir.pepotec.app.awesomeapp.model.blog

data class BlogData(
    val m_id:Int,
    val m_text:String,
    val m_date:String,
    val m_time:String,
    val like_num:Int,
    var liked:Boolean,
    var expand:Boolean = false,
    val file_id:Int,
    val user_id:Int,
    val s_id:String
)