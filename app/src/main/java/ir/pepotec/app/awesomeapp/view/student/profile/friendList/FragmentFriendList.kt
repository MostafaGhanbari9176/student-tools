package ir.pepotec.app.awesomeapp.view.student.profile.friendList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.student.profile.StudentProfileData
import ir.pepotec.app.awesomeapp.presenter.student.StudentProfilePresenter
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import ir.pepotec.app.awesomeapp.view.uses.ProgressInjection
import kotlinx.android.synthetic.main.fragment_friend_list.*

class FragmentFriendList:MyFragment(),ProgressInjection.ProgressInjectionListener {

    private lateinit var progress:ProgressInjection

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_friend_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        progress = ProgressInjection(this, ctx, view as ViewGroup, R.layout.fragment_friend_list)
        getData()
    }

    private fun getData() {
        progress.show()
        StudentProfilePresenter(object :StudentProfilePresenter.StudentProfileResult{
            override fun friendListData(ok: Boolean, message: String, data: ArrayList<StudentProfileData>?) {
                if(ok)
                {
                    progress.cancel()
                    setUpRV(data!!)
                }else
                    progress.error(message)
            }
        }).getFriendList()
    }

    private fun setUpRV(data: ArrayList<StudentProfileData>) {
        RVFriendList.layoutManager = GridLayoutManager(ctx, 1)
        RVFriendList.adapter = AdapterFriendList(data){
            (ctx as ActivityFriend).changeView(FragmentFriendProfile())
        }
    }

    override fun pressTryAgain() {
      getData()
    }


}