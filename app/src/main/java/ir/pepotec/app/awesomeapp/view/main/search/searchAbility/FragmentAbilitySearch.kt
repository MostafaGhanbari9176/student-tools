package ir.pepotec.app.awesomeapp.view.main.search.searchAbility

import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.*
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.student.ability.Ability
import ir.pepotec.app.awesomeapp.model.student.ability.AbilityData
import ir.pepotec.app.awesomeapp.model.student.profile.StudentProfileData
import ir.pepotec.app.awesomeapp.presenter.student.AbilityPresenter
import ir.pepotec.app.awesomeapp.presenter.student.StudentProfilePresenter
import ir.pepotec.app.awesomeapp.view.student.profile.activityProfile.ActivityProfile
import ir.pepotec.app.awesomeapp.view.student.profile.activityProfile.friendList.AdapterFriendList
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_search_list.*

class FragmentAbilitySearch : MyFragment() {

    private var reqKey: String = ""
    private var key: String = ""
    private var getAllData = false
    private var step: Int = 1
    private var adapter: AdapterAbilitySearch? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        txtTtlSearch.text = "جستوجوی مهارت های کار"
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
        AbilityPresenter(object : AbilityPresenter.AbilityResult {
            override fun searchData(ok: Boolean, message: String, data: ArrayList<AbilityData>?) {
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

    private fun newData(data: java.util.ArrayList<AbilityData>?) {
        adapter?.data?.addAll(data!!)
        adapter?.notifyDataSetChanged()
    }

    private fun setUpRV(data: java.util.ArrayList<AbilityData>?) {
        RVSearch.layoutManager = LinearLayoutManager(ctx)
        adapter = AdapterAbilitySearch(data!!, {
            startActivity(Intent(ctx, ActivityProfile::class.java).apply { putExtra("userId", it) })
        }, { reachedBottom() }
        )
        RVSearch.adapter = adapter
    }

    private fun reachedBottom() {
        if (getAllData)
            return
        LLSearchProgress.apply {
            visibility = View.VISIBLE
            (background as AnimatedVectorDrawable).start()
        }
        AbilityPresenter(object : AbilityPresenter.AbilityResult {
            override fun searchData(ok: Boolean, message: String, data: ArrayList<AbilityData>?) {
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
                setOnClickListener {
                    showSoftKeyBoard()
                    toast("[-_-{_-_('.')-_-}_-_]")
                }
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
        fadeImage.duration = 400
        transitionSet.addTransition(fadeImage)

        transitionSet.addListener(object:Transition.TransitionListener{
            override fun onTransitionEnd(transition: Transition) {
                view?.findViewById<TextView>(R.id.txtSearch)?.showSoftInputOnFocus
            }

            override fun onTransitionResume(transition: Transition) {
            }

            override fun onTransitionPause(transition: Transition) {
            }

            override fun onTransitionCancel(transition: Transition) {
            }

            override fun onTransitionStart(transition: Transition) {
            }
        })

        TransitionManager.go(listScene, transitionSet)
    }

    private fun showSoftKeyBoard() {
        val imm:InputMethodManager = ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

}