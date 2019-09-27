package ir.pepotec.app.awesomeapp.view.main.news

import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import cn.lightsky.infiniteindicator.ImageLoader
import cn.lightsky.infiniteindicator.IndicatorConfiguration
import cn.lightsky.infiniteindicator.InfiniteIndicator
import cn.lightsky.infiniteindicator.Page
import com.bumptech.glide.load.model.stream.HttpGlideUrlLoader
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.ApiClient
import ir.pepotec.app.awesomeapp.model.news.AgencyData
import ir.pepotec.app.awesomeapp.model.news.News
import ir.pepotec.app.awesomeapp.model.news.NewsData
import ir.pepotec.app.awesomeapp.model.news.NewsListData
import ir.pepotec.app.awesomeapp.model.user.UserDb
import ir.pepotec.app.awesomeapp.presenter.NewsPresenter
import ir.pepotec.app.awesomeapp.view.main.ActivityMain
import ir.pepotec.app.awesomeapp.view.uses.*
import kotlinx.android.synthetic.main.fragment_news.*

class FragmentNews : MyFragment() {

    private val data = ArrayList<NewsListData>()
    private val agencyData = ArrayList<AgencyData>()
    private val progress = DialogProgress { getListData() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBannerBounds()
        getBannerData()
        getListData()
    }

    private fun getBannerData() {
        NewsPresenter(object : NewsPresenter.Res {
            override fun singleList(ok: Boolean, message: String, data: ArrayList<NewsData>?) {
                if (ok)
                    setUpBanner(data!!)
            }
        }).special()
    }

    private fun setUpBanner(data: java.util.ArrayList<NewsData>) {
        val pageViews = ArrayList<Page>()

        data.forEach {
            pageViews.add(
                Page(
                    "${it.n_id}",
                    ApiClient.serverAddress + News.baseUrl + "getImg?phone=${UserDb().getUserPhone()}&apiCode=${UserDb().getUserApiCode()}&imgId=${it.n_id}"
                )
            )
        }


        val configuration = IndicatorConfiguration.Builder()
            .imageLoader(GlideLoader())
            .isAutoScroll(true)
            .isLoop(true)
            .isDrawIndicator(true)
            //.onPageClickListener(this)
            .direction(1)
            .position(IndicatorConfiguration.IndicatorPosition.Center_Bottom)
            .internal(5000)
            .scrollDurationFactor(5.0)
            .build()

        newsBanner.init(configuration)
        newsBanner.notifyDataChange(pageViews)
    }

    private fun setBannerBounds() {
        val point = Point()
        (ctx as ActivityMain).windowManager.defaultDisplay.getRealSize(point)
        newsBanner.layoutParams = RelativeLayout.LayoutParams(point.x, (point.x / 1.7).toInt())
    }


    private fun getListData() {
        if (data.size > 0) {
            setUpVP()
            return
        }
        progress.show()
        NewsPresenter(object : NewsPresenter.Res {
            override fun listData(
                ok: Boolean,
                message: String,
                data: ArrayList<NewsListData>?,
                agencyData: ArrayList<AgencyData>?
            ) {
                if (ok) {
                    progress.cancel()
                    this@FragmentNews.data.addAll(data!!)
                    this@FragmentNews.agencyData.addAll(agencyData!!)
                    setUpVP()
                } else
                    progress.error(message)

            }
        }).firstListData()
    }

    private fun setUpVP() {
        tabLayoutNews.setupWithViewPager(VPNews)
        val adapter = AdapterVP(childFragmentManager)
        data.reversed().forEach {
            val f = FragmentNewsList().apply { data = it.newsData; agencyData.addAll(this@FragmentNews.agencyData) }
            adapter.addData(VPModel(f, it.g_name))
        }
        VPNews.apply {
            this.adapter = adapter
            offscreenPageLimit = data.size
            currentItem = data.size - 1
        }
    }

}