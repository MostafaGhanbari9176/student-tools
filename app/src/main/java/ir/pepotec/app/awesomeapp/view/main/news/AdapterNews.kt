package ir.pepotec.app.awesomeapp.view.main.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.news.AgencyData
import ir.pepotec.app.awesomeapp.model.news.News
import ir.pepotec.app.awesomeapp.model.news.NewsData
import ir.pepotec.app.awesomeapp.view.main.ActivityMain
import ir.pepotec.app.awesomeapp.view.uses.AF
import ir.pepotec.app.awesomeapp.view.uses.App
import kotlinx.android.synthetic.main.item_news_list.view.*
import org.jetbrains.anko.toast

class AdapterNews(
    val data: ArrayList<NewsData>,
    private val agencyData: ArrayList<AgencyData>,
    private val listener: (newsId: Int) -> Unit,
    private val endOfList: (position: Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyHolder(v: View) : RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyHolder(LayoutInflater.from(App.instanse).inflate(R.layout.item_news_list, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyHolder).itemView.apply {
            with(data[position]) {
                txtSeenNewsItem.text = "$seen"
                txtAgencyNameItemNews.text = getAgencyName(agency_id)
                txtDateItemNews.text = n_date
                txtSubjectItemNews.text = n_sub
                txtLeadItemNews.text = lead
                AF().setImageWithBounds(imgItemNews, News.baseUrl + "getImg", n_id, false, cache = true)
                setOnClickListener {
                    listener(n_id)
                }
                if (position == itemCount - 1 && group_id == 1)
                    endOfList(position)
            }
        }
    }

    private fun getAgencyName(agencyId: Int): String {
        agencyData.forEach {
            if (it.a_id == agencyId)
                return it.a_sub
        }
        return ""
    }
}