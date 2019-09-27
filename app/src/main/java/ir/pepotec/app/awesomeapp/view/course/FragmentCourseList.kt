package ir.pepotec.app.awesomeapp.view.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.pepotec.app.awesomeapp.model.course.CourseData
import ir.pepotec.app.awesomeapp.view.main.ActivityMain
import ir.pepotec.app.awesomeapp.view.uses.App
import ir.pepotec.app.awesomeapp.view.uses.MyFragment

class FragmentCourseList : MyFragment() {
    var adapter: AdapterCourse? = null
    var data = ArrayList<CourseData>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return RecyclerView(App.instanse).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRV()
    }

    private fun setUpRV() {
        adapter = AdapterCourse(data){
            (ctx as ActivityMain).changeView(FragmentCourseMoreInfo().apply { courseId = it })
        }
        (view as RecyclerView).apply {
            adapter = this@FragmentCourseList.adapter
            layoutManager = LinearLayoutManager(ctx)

        }
    }
}

