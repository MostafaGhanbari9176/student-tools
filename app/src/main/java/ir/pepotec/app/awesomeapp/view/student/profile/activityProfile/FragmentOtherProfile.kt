package ir.pepotec.app.awesomeapp.view.student.profile.activityProfile

import android.content.Intent
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Fade
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.student.ability.AbilityList
import ir.pepotec.app.awesomeapp.model.student.profile.StudentProfile
import ir.pepotec.app.awesomeapp.model.student.profile.StudentProfileData
import ir.pepotec.app.awesomeapp.presenter.student.StudentProfilePresenter
import ir.pepotec.app.awesomeapp.view.student.ability.AdapterAbilityList
import ir.pepotec.app.awesomeapp.view.student.ability.activityAbility.FragmentShowAbility
import ir.pepotec.app.awesomeapp.view.chat.ActivityChat
import ir.pepotec.app.awesomeapp.view.uses.AF
import ir.pepotec.app.awesomeapp.view.uses.DialogProgress
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import ir.pepotec.app.awesomeapp.view.uses.ProgressInjection
import kotlinx.android.synthetic.main.fragment_other_profile.*

class FragmentOtherProfile : MyFragment() {

    var user_id = -1
    private val progress = DialogProgress()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_other_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }

    private fun getData() {
        progress.show()
        StudentProfilePresenter(object : StudentProfilePresenter.StudentProfileResult {
            override fun studentData(
                ok: Boolean,
                message: String,
                data: StudentProfileData?,
                abilityData: ArrayList<AbilityList>?
            ) {
                progress.cancel()
                if (ok) {
                    progress.cancel()
                    init(data!!, abilityData!!)
                }
            }
        }).otherProfile(user_id)
    }

    private fun init(data: StudentProfileData, abilityData: ArrayList<AbilityList>) {
        with(data) {
            txtAboutMeOtherProfile.text = about_me
            txtSIdOtherProfile.text = s_id
            txtNameOtherProfile.text = user_name
            txtPhoneOtherProfile.text = phone
            txtEmailOtherProfile.text = email
            txtLastSeenOtherProfile.text = last_date + " " + last_time
            if (about_me.isNotEmpty())
                startTransition()
            if (its_friend) {
                txtFriendOtherProfile.visibility = View.VISIBLE
                btnAddFriendOtherProfile.visibility = View.GONE
            }
        }
        setImage()
        if (abilityData.size == 0)
            LLAbilityHeadOtherProfile.visibility = View.GONE
        else
            setUpRV(abilityData)
        btnAddFriendOtherProfile.setOnClickListener {
            addFriend()
        }
        btnChatOtherProfile.setOnClickListener {
            startActivity(Intent(ctx, ActivityChat::class.java).apply {
                putExtra(
                    "chat_id",
                    user_id
                ); putExtra("kind_id", "s")
            })
        }

    }

    private fun addFriend() {
        StudentProfilePresenter(object : StudentProfilePresenter.StudentProfileResult {
            override fun addFriendRes(ok: Boolean, message: String) {
                if (ok) {
                    txtFriendOtherProfile.visibility = View.VISIBLE
                    btnAddFriendOtherProfile.visibility = View.GONE
                }
                toast(message)
            }
        }).addFriend(user_id)
    }

    private fun setImage() {
        AF().setImage(imgOtherProfile, StudentProfile.baseUrl+"downImg", user_id, false, cache = true)
    }

    private fun setUpRV(abilityData: java.util.ArrayList<AbilityList>) {
        RVAbilityOtherProfile.layoutManager = LinearLayoutManager(ctx)
        RVAbilityOtherProfile.adapter =
            AdapterAbilityList(abilityData, false) {
                (ctx as ActivityProfile).changeView(FragmentShowAbility().apply { abilityId = it; itsMy = false })
            }
    }

    private fun startTransition() {
        val set = TransitionSet().apply {
            addTransition(ChangeBounds())
            addTransition(Fade())
            startDelay = 500
        }
        TransitionManager.beginDelayedTransition(view as ViewGroup, set)
        txtAboutMeOtherProfile.visibility = View.VISIBLE
    }

}