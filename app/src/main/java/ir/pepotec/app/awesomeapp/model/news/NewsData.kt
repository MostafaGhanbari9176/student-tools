package ir.pepotec.app.awesomeapp.model.news

data class NewsData(
    val n_id:Int,
    val n_sub:String,
    val lead:String,
    val n_text:String,
    val agency_id:Int,
    val agencyName:String,
    val agencyLink:String,
    val group_id:Int,
    val n_date:String,
    val n_link:String,
    val seen:Int,
    val status:Int
)

data class NewsListData(
    val g_id:Int,
    val g_name:String,
    val newsData:ArrayList<NewsData>
)

data class AgencyData(
    val a_id:Int,
    val a_sub:String,
    val a_link:String
)

data class AgencyList(
    val agencyData:ArrayList<AgencyData>
)
