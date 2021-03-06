package ir.pepotec.app.awesomeapp.view.main.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.main.AdapterMainLists
import ir.pepotec.app.awesomeapp.view.main.MainListsModel
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_main_lists.*

class FragmentSearchList : MyFragment() {
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
        data.add(MainListsModel("جستوجو دانشجو", R.drawable.ic_student))
        data.add(MainListsModel("جستوجو مهارت کار", R.drawable.ic_ability))
        RVMainLists.adapter = AdapterMainLists(data) { position ->
            startActivity(Intent(ctx, ActivitySearch::class.java).apply { putExtra("flag",
                when(position){
                    0 -> "student"
                    1 -> "ability"
                    else -> "student"
                }
            ) })
        }
        RVMainLists.layoutManager = GridLayoutManager(ctx,3)
    }
}