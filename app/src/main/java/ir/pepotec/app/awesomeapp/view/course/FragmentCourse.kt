package ir.pepotec.app.awesomeapp.view.course

import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import cn.lightsky.infiniteindicator.IndicatorConfiguration
import cn.lightsky.infiniteindicator.Page
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.ApiClient
import ir.pepotec.app.awesomeapp.model.course.Course
import ir.pepotec.app.awesomeapp.model.course.CourseData
import ir.pepotec.app.awesomeapp.model.course.CourseListData
import ir.pepotec.app.awesomeapp.model.user.UserDb
import ir.pepotec.app.awesomeapp.presenter.CoursePresenter
import ir.pepotec.app.awesomeapp.view.main.ActivityMain
import ir.pepotec.app.awesomeapp.view.uses.*
import kotlinx.android.synthetic.main.fragment_news.*

class FragmentCourse : MyFragment() {

    private val progress = DialogProgress { getListData() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtTagNews.text = "ویژه"
        setBannerBounds()
        getBannerData()
        getListData()
    }

    private fun getBannerData() {
        CoursePresenter(object : CoursePresenter.Res {
            override fun singleList(ok: Boolean, message: String, data: ArrayList<CourseData>?) {
                if (ok)
                    setUpBanner(data!!)
            }
        }).special()
    }

    private fun setUpBanner(data: java.util.ArrayList<CourseData>) {
        val pageViews = ArrayList<Page>()

        data.forEach {
            pageViews.add(
                Page(
                    "${it.c_id}",
                    ApiClient.serverAddress + Course.baseUrl + "getImg?phone=${UserDb().getUserPhone()}&apiCode=${UserDb().getUserApiCode()}&imgId=${it.c_id}"
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
        progress.show()
        CoursePresenter(object : CoursePresenter.Res {
            override fun listData(
                ok: Boolean,
                message: String,
                data: ArrayList<CourseListData>?
            ) {
                if (ok) {
                    progress.cancel()
                    setUpVP(data!!)
                } else
                    progress.error(message)

            }
        }).firstListData()
    }

    private fun setUpVP(data: ArrayList<CourseListData>) {
        tabLayoutNews.setupWithViewPager(VPNews)
        val adapter = AdapterVP(childFragmentManager)
        data.reversed().forEach {
            val f = FragmentCourseList().apply { this.data = it.courseData }
            adapter.addData(VPModel(f, it.g_name))
        }
        VPNews.apply {
            setAdapter(adapter)
            offscreenPageLimit = data.size
            currentItem = data.size - 1
        }
    }

}