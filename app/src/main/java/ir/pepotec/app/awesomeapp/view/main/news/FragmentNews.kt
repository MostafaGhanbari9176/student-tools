package ir.pepotec.app.awesomeapp.view.main.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.news.AgencyData
import ir.pepotec.app.awesomeapp.model.news.NewsListData
import ir.pepotec.app.awesomeapp.presenter.NewsPresenter
import ir.pepotec.app.awesomeapp.view.main.ActivityMain
import ir.pepotec.app.awesomeapp.view.uses.AdapterVP
import ir.pepotec.app.awesomeapp.view.uses.DialogProgress
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import ir.pepotec.app.awesomeapp.view.uses.VPModel
import kotlinx.android.synthetic.main.fragment_news.*

class FragmentNews :MyFragment() {

    private val data = ArrayList<NewsListData>()
    private val agencyData = ArrayList<AgencyData>()
    private val progress = DialogProgress()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getListData()
    }

    private fun getListData() {
        progress.show()
        NewsPresenter(object:NewsPresenter.Res{
            override fun listData(
                ok: Boolean,
                message: String,
                data: ArrayList<NewsListData>?,
                agencyData: ArrayList<AgencyData>?
            ) {
                progress.cancel()
                if(ok)
                {
                    this@FragmentNews.data.addAll(data!!)
                    this@FragmentNews.agencyData.addAll(agencyData!!)
                    setUpVP()
                }else
                (ctx as ActivityMain).onBackPressed()

            }
        }).firstListData()
    }

    private fun setUpVP() {
        tabLayoutNews.setupWithViewPager(VPNews)
        val adapter = AdapterVP(childFragmentManager)
        for(o in data)
        {
            val f = FragmentNewsList().apply { data = o.newsData }
            adapter.addData(VPModel(f, o.g_name))
        }
        VPNews.apply {
            offscreenPageLimit = data.size
            setCurrentItem(data.size - 1)
        }
    }

}