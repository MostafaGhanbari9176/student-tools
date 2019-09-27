package ir.pepotec.app.awesomeapp.view.main.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.pepotec.app.awesomeapp.model.news.AgencyData
import ir.pepotec.app.awesomeapp.model.news.NewsData
import ir.pepotec.app.awesomeapp.view.main.ActivityMain
import ir.pepotec.app.awesomeapp.view.uses.App
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_news.*

class FragmentNewsList:MyFragment() {

    var adapter:AdapterNews?= null
    var data = ArrayList<NewsData>()
    val agencyData = ArrayList<AgencyData>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return RecyclerView(ctx).apply {
            layoutDirection = View.LAYOUT_DIRECTION_RTL
            layoutParams = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.MATCH_PARENT
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpRV()
    }

    private fun setUpRV() {
        adapter = AdapterNews(data, agencyData,{showMore(it)},{})
        (view as RecyclerView).apply {
            adapter = this@FragmentNewsList.adapter
            layoutManager = LinearLayoutManager(ctx)

        }
    }

    private fun showMore(newsId: Int) {
        (ctx as ActivityMain).changeView(FragmentNewsMoreInfo().apply { this.newsId = newsId })
    }

}