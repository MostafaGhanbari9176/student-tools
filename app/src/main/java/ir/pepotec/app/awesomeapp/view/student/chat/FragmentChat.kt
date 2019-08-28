package ir.pepotec.app.awesomeapp.view.student.chat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.main.search.ActivitySearch
import ir.pepotec.app.awesomeapp.view.student.ActivityStudent
import ir.pepotec.app.awesomeapp.view.student.chat.chatList.FragmentChatList
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.content_common.*

class FragmentChat:MyFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.content_common, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        fabCC.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                startActivity(Intent(ctx, ActivitySearch::class.java).apply { putExtra("key", "student") })
            }
        }
        val f = FragmentChatList()
        f.parent = this
        changeView(f)
    }

    fun changeView(f:MyFragment)
    {
        (ctx as ActivityStudent).supportFragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.ContentCommon, f).commit()
    }

}