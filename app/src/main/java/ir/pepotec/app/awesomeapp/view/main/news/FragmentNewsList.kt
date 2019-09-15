package ir.pepotec.app.awesomeapp.view.main.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.pepotec.app.awesomeapp.model.news.NewsData
import ir.pepotec.app.awesomeapp.view.uses.App
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_news.*

class FragmentNewsList:MyFragment() {

    var adapter:AdapterNews?= null
    var data = ArrayList<NewsData>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return RecyclerView(App.instanse).apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRV()
    }

    private fun setUpRV() {
        adapter = AdapterNews(data)
        (view as RecyclerView).apply {
            adapter = this@FragmentNewsList.adapter
            layoutManager = LinearLayoutManager(ctx)

        }
    }

}