package ir.pepotec.app.awesomeapp.view.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.blog.ActivityBlog
import ir.pepotec.app.awesomeapp.view.blog.AdapterBlog
import ir.pepotec.app.awesomeapp.view.student.ActivityStudent
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_main_lists.*

class FragmentHomeList : MyFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_lists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        homeList()
    }

    private fun homeList() {
        val data = ArrayList<MainListsModel>()
        data.add(MainListsModel("دانشجو", R.drawable.ic_student))
        data.add(MainListsModel("وبلاگ", R.drawable.ic_subtitles))
        RVMainLists.adapter = AdapterMainLists(data) { position ->
            when (position) {
                0 -> startActivity(Intent(ctx, ActivityStudent::class.java))
                1 -> startActivity(Intent(ctx, ActivityBlog::class.java))
            }
        }
        RVMainLists.layoutManager = GridLayoutManager(ctx,3)
    }
}