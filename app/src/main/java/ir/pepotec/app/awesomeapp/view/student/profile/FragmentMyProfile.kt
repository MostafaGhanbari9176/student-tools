package ir.pepotec.app.awesomeapp.view.student.profile

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.student.ability.AbilityList
import ir.pepotec.app.awesomeapp.model.student.profile.StudentProfile
import ir.pepotec.app.awesomeapp.model.student.profile.StudentProfileData
import ir.pepotec.app.awesomeapp.model.student.profile.StudentProfileDb
import ir.pepotec.app.awesomeapp.model.user.UserDb
import ir.pepotec.app.awesomeapp.presenter.student.StudentProfilePresenter
import ir.pepotec.app.awesomeapp.view.student.profile.activityProfile.ActivityProfile
import ir.pepotec.app.awesomeapp.view.uses.AF
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import ir.pepotec.app.awesomeapp.view.uses.ProgressInjection
import kotlinx.android.synthetic.main.fragment_profile.*

class FragmentMyProfile : MyFragment(), ProgressInjection.ProgressInjectionListener {

    private val name = "user_name"
    private val phone = "phone"
    private val message = "message"

    private lateinit var progressInjection: ProgressInjection

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressInjection = ProgressInjection(this, ctx, view as ViewGroup, R.layout.fragment_profile)
        checkDataExist()
    }

    private fun init() {
        profileHead.initialize(imgProfile, R.color.tabBack, R.color.colorPrimaryDark)
        setImage(false)
        animateParent()
        imgProfile.setOnClickListener {
            DialogProfileImg()
        }
        txtMyName.setOnClickListener {
            DialogEyeLevel(
                getString(R.string.allEyeLevel),
                getString(R.string.friendEyeLevel),
                false,
                name
            ).defaultChose(false)
        }
        txtMyPhone.setOnClickListener {
            DialogEyeLevel(
                getString(R.string.allEyeLevel),
                getString(R.string.friendEyeLevel),
                true,
                phone
            ).defaultChose(false)

        }
        btnAboutMyProfile.setOnClickListener {
            DialogAboutMe()
        }
        btnFriendListMyProfile.setOnClickListener {
            startActivity(Intent(ctx, ActivityProfile::class.java))
        }
    }

    private fun checkDataExist() {
        val sId = StudentProfileDb().getStudentId()
        if (sId.isEmpty())
            getStudentData()
        else
            setData()
    }

    private fun getStudentData() {
        progressInjection.show()
        StudentProfilePresenter(object : StudentProfilePresenter.StudentProfileResult {
            override fun studentData(
                ok: Boolean,
                message: String,
                data: StudentProfileData?,
                abilityData: ArrayList<AbilityList>?
            ) {
                if (ok) {
                    progressInjection.cancel()
                    init()
                    val phone = UserDb().getUserPhone()
                    txtMyStudentId.text = data?.s_id ?: "!"
                    txtMyName.text = data?.user_name ?: "!"
                    txtMyPhone.text = phone

                } else
                    progressInjection.error(message)
            }
        }).myProfile()
    }

    private fun setData() {
        init()
        val sId = StudentProfileDb().getStudentId()
        val name = StudentProfileDb().getStudentName()
        val phone = UserDb().getUserPhone()

        txtMyStudentId.text = sId
        txtMyName.text = name
        txtMyPhone.text = phone
    }

    private fun setImage(new:Boolean) {
        AF().setImage(imgProfile, StudentProfile.baseUrl+"downMyImg", 0, new, true)
    }

    fun changeImg(b: Bitmap) {
        val file = AF().convertBitMapToFile(b, ctx, "profile")
        StudentProfilePresenter(object : StudentProfilePresenter.StudentProfileResult {
            override fun upStudentImgRes(ok: Boolean) {
                setImage(true)
            }
        }).upMyImg(file)
    }

    private fun animateParent() {
        ObjectAnimator.ofFloat(profileViewParent, View.ALPHA, 0f, 1f).apply {
            startDelay = 350
            duration = 250
            interpolator = AccelerateInterpolator()
            start()
        }
    }

    override fun pressTryAgain() {
        getStudentData()
    }

}