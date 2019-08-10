package ir.pepotec.app.awesomeapp.view.student.profile.activityProfile.friendList

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.student.profile.StudentProfileData
import ir.pepotec.app.awesomeapp.presenter.student.StudentProfilePresenter
import ir.pepotec.app.awesomeapp.view.main.search.activitySearch.ActivitySearch
import ir.pepotec.app.awesomeapp.view.student.profile.activityProfile.ActivityProfile
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import ir.pepotec.app.awesomeapp.view.uses.MyRecyclerCallBack
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.fragment_friend_list.*

class FragmentFriendList:MyFragment() {

    lateinit var adapter:AdapterFriendList

    var deletePosition = -1
    lateinit var deleteData:StudentProfileData

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_friend_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        getData()
        (ctx as ActivityProfile).fabActivityProfile.setOnClickListener {
            startActivity(Intent(ctx, ActivitySearch::class.java).apply { putExtra("key", "student") })
        }
    }

    private fun getData() {
        StudentProfilePresenter(object :StudentProfilePresenter.StudentProfileResult{
            override fun friendListData(ok: Boolean, message: String, data: ArrayList<StudentProfileData>?) {
                setUpRv(data)
            }
        }).getFriendList()
    }

    private fun setUpRv(data: ArrayList<StudentProfileData>?) {
        adapter = AdapterFriendList(data!!, {}, {})
        RVFrienList.layoutManager = LinearLayoutManager(ctx)
        RVFrienList.adapter = adapter
        val touchHelper = ItemTouchHelper(MyRecyclerCallBack(object:MyRecyclerCallBack.RecyclerCallBack{
            override fun leftSwipe(position: Int) {
                deleteItem(position)
            }

            override fun rightSwipe(position: Int) {
                adapter.notifyItemChanged(position)
            }
        },R.drawable.ic_delete, R.drawable.ic_sms, R.color.redLight, R.color.colorAccent,true, false))
        touchHelper.attachToRecyclerView(RVFrienList)
    }

    private fun deleteItem(position: Int) {
        if(deletePosition != -1)
            deleteFromModel()
        deletePosition = position
        deleteData = adapter.data[position]
        adapter.data.removeAt(position)
        adapter.notifyItemRemoved(position)
        showSnakeBar()
    }

    private fun showSnakeBar() {
        Snackbar.make(RVFrienList, "درحال حذف ${deleteData.s_id} از لیست دوستان", Snackbar.LENGTH_LONG).apply {
            setAction("لغو حذف"){undoDelete()}
            addCallback(object :Snackbar.Callback(){
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)
                    if(event == 2)
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
        StudentProfilePresenter(object:StudentProfilePresenter.StudentProfileResult{
            override fun addFriendRes(ok: Boolean, message: String) {
                if(ok)
                    toast(message)
            }
        }).deleteFriend(deleteData.user_id)
    }

    private fun undoDelete() {
        adapter.data.add(deletePosition, deleteData)
        adapter.notifyItemInserted(deletePosition)
        deletePosition = -1
    }

}