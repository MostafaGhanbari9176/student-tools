package ir.pepotec.app.awesomeapp.view.course

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.course.Course
import ir.pepotec.app.awesomeapp.model.course.CourseData
import ir.pepotec.app.awesomeapp.model.news.News
import ir.pepotec.app.awesomeapp.model.news.NewsData
import ir.pepotec.app.awesomeapp.view.uses.AF
import ir.pepotec.app.awesomeapp.view.uses.App
import kotlinx.android.synthetic.main.item_course.view.*
import kotlinx.android.synthetic.main.item_news_list.view.*

class AdapterCourse(val data:ArrayList<CourseData>, private val listener:(courseId:Int) -> Unit):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyHolder(v: View):RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyHolder(LayoutInflater.from(App.instanse).inflate(R.layout.item_course, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyHolder).itemView.apply {
            with(data[position]) {
                txtDateItemCourse.text = c_date
                txtSubItemCourse.text = c_name
                txtOwnerNameItemCourse.text = owner_name
                txtSeenItemCourse.text = seen.toString()
                setOnClickListener {
                    listener(c_id)
                }
                AF().setImageWithBounds(imgItemCourse, Course.baseUrl+"getImg", c_id, false, cache = true)
            }
        }
    }
}