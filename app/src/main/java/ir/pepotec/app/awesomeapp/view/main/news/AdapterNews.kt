package ir.pepotec.app.awesomeapp.view.main.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.news.NewsData
import ir.pepotec.app.awesomeapp.view.uses.App
import kotlinx.android.synthetic.main.item_news_list.view.*

class AdapterNews(val data:ArrayList<NewsData>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyHolder(v: View):RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyHolder(LayoutInflater.from(App.instanse).inflate(R.layout.item_news_list, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyHolder).itemView.apply {
            with(data[position]) {
                txtDateItemNews.text = n_date
                txtSubjectItemNews.text = n_sub
                txtLeadItemNews.text = lead
            }
        }
    }
}