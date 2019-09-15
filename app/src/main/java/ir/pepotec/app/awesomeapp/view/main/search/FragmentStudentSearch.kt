package ir.pepotec.app.awesomeapp.view.main.search

import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.*
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.student.profile.StudentProfileData
import ir.pepotec.app.awesomeapp.presenter.InvitePresenter
import ir.pepotec.app.awesomeapp.presenter.student.StudentProfilePresenter
import ir.pepotec.app.awesomeapp.view.student.profile.activityProfile.ActivityProfile
import ir.pepotec.app.awesomeapp.view.student.profile.activityProfile.friendList.AdapterFriendList
import ir.pepotec.app.awesomeapp.view.uses.DialogProgress
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_search_list.*

class FragmentStudentSearch : MyFragment() {

    var groupId = -1
    private var reqKey: String = ""
    private var key: String = ""
    private var getAllData = false
    private var step: Int = 1
    private var adapter: AdapterFriendList? = null
    private val progress = DialogProgress()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        txtTtlSearch.text = "جستوجوی دانشجویان"
        txtSearch.setOnTouchListener { v, event ->
            startTransition()
            false
        }

    }

    private fun getData() {
        if (reqKey.isEmpty())
            reqKey = key
        else
            return
        getAllData = false
        step = 1
        LLSearchProgress.apply {
            visibility = View.VISIBLE
            (background as AnimatedVectorDrawable).start()
        }
        StudentProfilePresenter(object : StudentProfilePresenter.StudentProfileResult {
            override fun searchRes(ok: Boolean, message: String, data: ArrayList<StudentProfileData>?) {
                if(reqKey.isNotEmpty() && reqKey != key) {
                    reqKey = ""
                    getData()
                }
                reqKey = ""
                LLSearchProgress.apply {
                    visibility = View.GONE
                    (background as AnimatedVectorDrawable).stop()
                }
                if (ok)
                    setUpRV(data)
                else
                    toast(message)

            }
        }).search(key, 10, step)
    }

    private fun newData(data: java.util.ArrayList<StudentProfileData>?) {
        adapter?.data?.addAll(data!!)
        adapter?.notifyDataSetChanged()
    }

    private fun setUpRV(data: java.util.ArrayList<StudentProfileData>?) {
        RVSearch.layoutManager = LinearLayoutManager(ctx)
        adapter = AdapterFriendList(data!!, {
            if(groupId == -1)
            startActivity(Intent(ctx, ActivityProfile::class.java).apply { putExtra("userId", it) })
            else
                inviteAnswer(it)
        }, { reachedBottom() }
        )
        RVSearch.adapter = adapter
    }

    private fun inviteAnswer(userId:Int) {
        AlertDialog.Builder(ctx)
            .setTitle("از دعوت این شخص مطمعن هستید؟")
            .setPositiveButton("بله", DialogInterface.OnClickListener{ dialog, which -> invite(userId) })
            .setNegativeButton("خیر", null)
            .show()
    }

    private fun invite(userId: Int) {
        progress.show()
        InvitePresenter(object:InvitePresenter.Res{
            override fun result(ok: Boolean, message: String) {
                progress.cancel()
                toast(message)
            }
        }).add(groupId, userId)
    }

    private fun reachedBottom() {
        if (getAllData)
            return
        LLSearchProgress.apply {
            visibility = View.VISIBLE
            (background as AnimatedVectorDrawable).start()
        }
        StudentProfilePresenter(object : StudentProfilePresenter.StudentProfileResult {
            override fun searchRes(ok: Boolean, message: String, data: ArrayList<StudentProfileData>?) {
                LLSearchProgress.apply {
                    visibility = View.GONE
                    (background as AnimatedVectorDrawable).stop()
                }
                if (ok) {
                    if (data?.size == 0)
                        getAllData = true
                    newData(data)
                }
            }
        }).search(key, 10, ++step)
    }

    private fun startTransition() {
        val transitionRoot: ViewGroup = LLSearchParent
        val listScene: Scene = Scene.getSceneForLayout(transitionRoot, R.layout.fragment_search_list, ctx)
        listScene.setEnterAction(Runnable {
            view?.findViewById<TextView>(R.id.txtSearch)?.apply {
                requestFocus()
                addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {

                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        key = s.toString()
                        getData()
                    }
                })
            }
        })
        val transitionSet = TransitionSet()
        transitionSet.ordering = TransitionSet.ORDERING_TOGETHER

        val changeBounds = ChangeBounds()
        changeBounds.duration = 300
        changeBounds.startDelay = 100
        transitionSet.addTransition(changeBounds)

        val fadeImage = Fade()
        fadeImage.addTarget(LLHeadSearch)
        //fadeImage.addTarget(LLSearchProgress)
        fadeImage.duration = 400
        transitionSet.addTransition(fadeImage)

        TransitionManager.go(listScene, transitionSet)

    }

}