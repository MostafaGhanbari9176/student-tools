package ir.pepotec.app.awesomeapp.view.chat.chatList

import android.os.Bundle
import android.view.*
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.chat.ActivityChat
import ir.pepotec.app.awesomeapp.view.uses.AdapterVP
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import ir.pepotec.app.awesomeapp.view.uses.VPModel
import kotlinx.android.synthetic.main.fragment_chat.*

class FragmentChat : MyFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (ctx as ActivityChat).apply {
            setSupportActionBar(tlbChat)
            supportActionBar?.title = ""
        }
    }

    private fun setUpVP() {
        tabLayoutChat.setupWithViewPager(VPChat)
        val adapter = AdapterVP(childFragmentManager)
        adapter.addData(VPModel(FragmentChatList().apply { kind_id = "g" }, "گروه گفتوگو"))
        adapter.addData(VPModel(FragmentChatList().apply { kind_id = "s" }, "گفتوگو"))
        VPChat.apply {
            this.adapter = adapter
            currentItem = 1
            offscreenPageLimit = 2
        }

    }

    override fun onResume() {
        super.onResume()
        setUpVP()
    }

}