package ir.pepotec.app.awesomeapp.view.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.student.ActivityStudent
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_home.*

class FragmentHome : MyFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        homeList()
    }

    private fun homeList() {
        RVHome.adapter = AdapterHomeList{
            position ->
            startActivity(Intent(ctx, ActivityStudent::class.java))
            //toast("$position")
        }
        RVHome.layoutManager = GridLayoutManager(ctx,2)
    }
}