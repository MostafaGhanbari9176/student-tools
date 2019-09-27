package ir.pepotec.app.awesomeapp.view.main.news

import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.news.News
import ir.pepotec.app.awesomeapp.model.news.NewsData
import ir.pepotec.app.awesomeapp.presenter.NewsPresenter
import ir.pepotec.app.awesomeapp.view.main.ActivityMain
import ir.pepotec.app.awesomeapp.view.uses.AF
import ir.pepotec.app.awesomeapp.view.uses.DialogProgress
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_more.*

class FragmentNewsMoreInfo : MyFragment() {

    var newsId = -1
    private val progress = DialogProgress { getData() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setBannerBounds()
        getData()
    }

    private fun setBannerBounds() {
        val point = Point()
        (ctx as ActivityMain).windowManager.defaultDisplay.getRealSize(point)
        imgMore.layoutParams = RelativeLayout.LayoutParams(point.x, (point.x / 1.7).toInt())
    }

    private fun getData() {
        progress.show()
        NewsPresenter(object : NewsPresenter.Res {
            override fun single(ok: Boolean, message: String, data: NewsData?) {
                if (ok) {
                    progress.cancel()
                    setData(data!!)
                } else {
                    progress.error(message)
                }
            }
        }).get(newsId)
    }

    private fun setData(data: NewsData) {
        setBannerImage()
        with(data)
        {
            txtOwnerMore.text = "خبرگذاری : " + agencyName
            txtDateMore.text = n_date
            txtSeenMore.text = "$seen"
            txtSubMore.text = "عنوان : " + n_sub
            txtMore.text = n_text
            txtLink1More.apply {
                visibility = View.VISIBLE
                text = n_link
            }
            txtLink2More.apply {
                visibility = View.VISIBLE
                text = agencyLink
            }
        }
    }

    private fun setBannerImage() {
        AF().setImageWithBounds(imgMore, News.baseUrl + "getImg", newsId, false, cache = true)
    }
}
