package ir.pepotec.app.awesomeapp.view.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.groupChat.GroupChatData
import ir.pepotec.app.awesomeapp.presenter.GroupChatPresenter
import ir.pepotec.app.awesomeapp.view.uses.DialogProgress
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_add_group_chat.*

class FragmentAddGroupChat : MyFragment() {

    private val progress = DialogProgress { checkData() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_group_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        btnSaveAddGroupChat.setOnClickListener {
            checkData()
        }
        rbInviteOnlyAddGroupChat.setOnCheckedChangeListener { buttonView, isChecked ->
            cbxInviteAccessAddGroupChat.visibility =
                if (!rbInviteOnlyAddGroupChat.isChecked) View.GONE else View.VISIBLE
        }
        rbInviteOnlyAddGroupChat.isChecked = true
    }

    private fun checkData() {
        val sub = txtSubjectAddGroupChat.text.toString().trim()
        if (sub.isEmpty()) {
            txtSubjectAddGroupChat.apply {
                error = "لطفا پرکنید!"
                requestFocus()
            }
            return
        }
        val info = txtInfoAddGroupChat.text.toString().trim()
        if (info.isEmpty()) {
            txtInfoAddGroupChat.apply {
                error = "لطفا پرکنید!"
                requestFocus()
            }
            return
        }

        if (!rbInviteOnlyAddGroupChat.isChecked)
            cbxInviteAccessAddGroupChat.isChecked = false

        saveData(sub, info)
    }

    private fun saveData(name: String, info: String) {
        progress.show()
        GroupChatPresenter(object : GroupChatPresenter.Res {
            override fun result(ok: Boolean, message: String, data: GroupChatData?) {
                if (ok) {
                    progress.cancel()
                    toast(message)
                    (ctx as ActivityChat).onBackPressed()
                } else
                    progress.error(message)
            }
        }).add(
            name,
            info,
            if (rbInviteOnlyAddGroupChat.isChecked) 0 else 1,
            if (cbxInviteAccessAddGroupChat.isChecked) 1 else 0
        )
    }

}