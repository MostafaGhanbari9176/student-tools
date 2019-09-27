package ir.pepotec.app.awesomeapp.view.chat

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Fade
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.groupChat.GroupChat
import ir.pepotec.app.awesomeapp.model.groupChat.GroupChatData
import ir.pepotec.app.awesomeapp.model.student.profile.StudentProfileData
import ir.pepotec.app.awesomeapp.presenter.GroupChatPresenter
import ir.pepotec.app.awesomeapp.view.main.search.FragmentStudentSearch
import ir.pepotec.app.awesomeapp.view.student.ActivityStudent
import ir.pepotec.app.awesomeapp.view.student.profile.activityProfile.FragmentOtherProfile
import ir.pepotec.app.awesomeapp.view.student.profile.activityProfile.friendList.AdapterFriendList
import ir.pepotec.app.awesomeapp.view.uses.AF
import ir.pepotec.app.awesomeapp.view.uses.DialogProgress
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import ir.pepotec.app.awesomeapp.view.uses.MyRecyclerCallBack
import kotlinx.android.synthetic.main.fragment_group_chat_data.*

class FragmentGroupChatData : MyFragment() {

    private val progress = DialogProgress { getData() }
    var groupId = -1
    private var studentAdapter: AdapterFriendList? = null
    private var admin = false
    private var deletePosition = -1
    private lateinit var deleteData: StudentProfileData

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_group_chat_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        getData()
    }

    private fun init() {
        btnLeftGroupChatData.setOnClickListener {
            left()
        }
        btnAddMemberGroupChatData.setOnClickListener {
            invite()
        }
        imgGroupChatData.setOnClickListener {
            choseImg()
        }
    }

    private fun choseImg() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        ActivityCompat.startActivityForResult(
            ctx as ActivityChat,
            Intent.createChooser(intent, "Select Picture"),
            2,
            null
        )
    }

    private fun invite() {
        (ctx as ActivityChat).changeView(FragmentStudentSearch().apply { groupId = this@FragmentGroupChatData.groupId })
    }

    private fun left() {
        progress.show()
        GroupChatPresenter(object : GroupChatPresenter.Res {
            override fun result(ok: Boolean, message: String, data: GroupChatData?) {
                progress.cancel()
                toast(message)
                if (ok)
                    (ctx as ActivityChat).onBackPressed()
            }
        }).left(groupId)
    }

    private fun getData() {
        progress.show()
        GroupChatPresenter(object : GroupChatPresenter.Res {
            override fun result(ok: Boolean, message: String, data: GroupChatData?) {
                if (ok) {
                progress.cancel()
                    setData(data!!)
                    getMemberList()
                } else
                    progress.error(message)

            }
        }).getData(groupId)
    }

    private fun getMemberList() {
        GroupChatPresenter(object : GroupChatPresenter.Res {
            override fun memberList(
                ok: Boolean,
                message: String,
                data: ArrayList<StudentProfileData>?
            ) {
                pbarGroupChatData.visibility = View.GONE
                if (ok)
                    setUpRV(data)
            }
        }).memberList(groupId)
    }

    private fun setUpRV(data: ArrayList<StudentProfileData>?) {
        studentAdapter = AdapterFriendList(data!!, {
            (ctx as ActivityChat).changeView(FragmentOtherProfile().apply { user_id = it })
        }, {})
        RVMemberGroupChatData.apply {
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(ctx)
        }
        if (admin)
            setUpTouchHelper()
    }

    private fun setUpTouchHelper() {
        val touchHelper = ItemTouchHelper(
            MyRecyclerCallBack(
                object : MyRecyclerCallBack.RecyclerCallBack {
                    override fun leftSwipe(position: Int) {
                        deleteMember(position)
                    }

                    override fun rightSwipe(position: Int) {
                        deleteMember(position)
                    }

                },
                R.drawable.ic_delete, R.drawable.ic_delete, R.color.redLight, R.color.redLight,
                rightLimit = false, leftLimit = false
            )
        )
        touchHelper.attachToRecyclerView(RVMemberGroupChatData)
    }

    private fun deleteMember(position: Int) {

        if (deletePosition != -1)
            deleteFromModel()
        deletePosition = position
        deleteData = studentAdapter!!.data[position]
        studentAdapter!!.data.removeAt(position)
        studentAdapter!!.notifyItemRemoved(position)
        showSnakeBar()
    }

    private fun showSnakeBar() {
        Snackbar.make(RVMemberGroupChatData, "درحال حذف ${deleteData.s_id} از میان اعضاء", Snackbar.LENGTH_LONG).apply {
            setAction("لغو حذف") { undoDelete() }
            addCallback(object : Snackbar.Callback() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)
                    if (event == 2)
                        deleteFromModel()
                }
            })
            setActionTextColor(ContextCompat.getColor(ctx, R.color.colorAccent))
            setTextColor(Color.RED)
            show()
        }

    }

    private fun deleteFromModel() {
        deletePosition = -1
        GroupChatPresenter(object : GroupChatPresenter.Res {
            override fun result(ok: Boolean, message: String, data: GroupChatData?) {

                if (ok)
                    toast(message)
            }
        }).removeMember(groupId, deleteData.user_id)
    }

    private fun undoDelete() {
        studentAdapter!!.data.add(deletePosition, deleteData)
        studentAdapter!!.notifyItemInserted(deletePosition)
        deletePosition = -1
    }

    private fun setData(data: GroupChatData) {
        admin = data.admin
        with(data) {
            txtInfoGroupChatData.text = g_info
            txtChatSubjectGroupChatData.text = g_name
            txtCreateDateGroupChatData.text = "$c_date $c_time"
            txtAdminGroupChatData.visibility = if (admin) View.VISIBLE else View.GONE
            LLAdminButtonGroupChatData.visibility = if (admin) View.VISIBLE else View.GONE
            btnLeftGroupChatData.visibility = if (!admin) View.VISIBLE else View.GONE
            txtInviteModeGroupChatData.text = "نحوه عضویت : " + inviteText(invite_mode, join_mode)
            if (g_info.isNotEmpty())
                startTransition()
        }
        setImage()
    }

    fun setImage() {
        AF().setImage(imgGroupChatData, GroupChat.baseUrl + "downImg", groupId, false, cache = false)
    }

    private fun startTransition() {
        val set = TransitionSet().apply {
            addTransition(Fade())
            addTransition(ChangeBounds())
            startDelay = 500
        }
        TransitionManager.beginDelayedTransition(view as ViewGroup, set)
        txtInfoGroupChatData.visibility = View.VISIBLE
    }

    private fun inviteText(inviteMode: Int, joinMode: Int): String {
        return if (joinMode == 0) "فقط از طریق دعوت"
        else "همه میتوانند عظو شوند"
    }


}