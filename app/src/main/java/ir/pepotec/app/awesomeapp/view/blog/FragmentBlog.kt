package ir.pepotec.app.awesomeapp.view.blog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.student.AdapterVPStudent
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import ir.pepotec.app.awesomeapp.view.uses.VPModel
import kotlinx.android.synthetic.main.fragment_blog.*

class FragmentBlog : MyFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_blog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpVP()
        btnAddMessageBlog.setOnClickListener {
            (ctx as ActivityBlog).changeView(FragmentAbbBlog())
        }
    }

    private fun setUpVP() {
        val adapter = AdapterVPStudent(childFragmentManager)
        tabLayoutBlog.setupWithViewPager(VPBlog)
        val f1 = FragmentBlogData()
        f1.dataMethode = "all"
        adapter.addData(VPModel(f1, "همه"))
        tabLayoutBlog.setupWithViewPager(VPBlog)
        val f2 = FragmentBlogData()
        f2.dataMethode = "like"
        adapter.addData(VPModel(f2, "محبوب"))
        VPBlog.adapter = adapter
        VPBlog.setCurrentItem(1)
        VPBlog.offscreenPageLimit = 3
    }
}