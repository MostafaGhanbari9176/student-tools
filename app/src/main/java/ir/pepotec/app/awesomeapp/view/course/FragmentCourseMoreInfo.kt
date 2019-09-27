package ir.pepotec.app.awesomeapp.view.course

import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.course.Course
import ir.pepotec.app.awesomeapp.model.course.CourseData
import ir.pepotec.app.awesomeapp.model.news.News
import ir.pepotec.app.awesomeapp.model.news.NewsData
import ir.pepotec.app.awesomeapp.presenter.CoursePresenter
import ir.pepotec.app.awesomeapp.presenter.NewsPresenter
import ir.pepotec.app.awesomeapp.view.main.ActivityMain
import ir.pepotec.app.awesomeapp.view.uses.AF
import ir.pepotec.app.awesomeapp.view.uses.DialogProgress
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_more.*

class FragmentCourseMoreInfo : MyFragment() {

    var courseId = -1
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
        CoursePresenter(object : CoursePresenter.Res {
            override fun single(ok: Boolean, message: String, data: CourseData?) {
                if (ok) {
                    progress.cancel()
                    setData(data!!)
                } else {
                    progress.error(message)
                }
            }
        }).get(courseId)
    }

    private fun setData(data: CourseData) {
        setBannerImage()
        with(data)
        {
            txtOwnerMore.text = "برگذار کننده : " + owner_name
            txtDateMore.text = c_date
            txtSeenMore.text = "$seen"
            txtSubMore.text = "عنوان : " + c_name
            txtMore.text = c_text
            if (!phone.isNullOrEmpty()) {
                txtPhoneMore.apply {
                    visibility = View.VISIBLE
                    text = phone
                }
            }
            if (!email.isNullOrEmpty()) {
                txtEmailMore.apply {
                    visibility = View.VISIBLE
                    text = email
                }
            }
            if (!website.isNullOrEmpty()) {
                txtLink1More.apply {
                    visibility = View.VISIBLE
                    text = website
                }
            }
            if (!social.isNullOrEmpty()) {
                txtSocialMore.apply {
                    visibility = View.VISIBLE
                    text = social
                }
            }
        }
    }

    private fun setBannerImage() {
        AF().setImageWithBounds(imgMore, Course.baseUrl + "getImg", courseId, false, cache = true)
    }
}
