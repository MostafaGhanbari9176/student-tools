package ir.pepotec.app.awesomeapp.view.student.profile

import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import com.bumptech.glide.Glide
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.ApiClient
import ir.pepotec.app.awesomeapp.model.student.profile.StudentProfileData
import ir.pepotec.app.awesomeapp.model.student.profile.StudentProfileDb
import ir.pepotec.app.awesomeapp.model.student.workSample.WorkSampleApi
import ir.pepotec.app.awesomeapp.model.user.UserDb
import ir.pepotec.app.awesomeapp.presenter.student.StudentProfilePresenter
import ir.pepotec.app.awesomeapp.view.student.profile.friendList.ActivityFriend
import ir.pepotec.app.awesomeapp.view.uses.AbsoluteFunctions
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import ir.pepotec.app.awesomeapp.view.uses.ProgressInjection
import kotlinx.android.synthetic.main.fragment_profile.*
import okhttp3.ResponseBody
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class FragmentProfile : MyFragment(), ProgressInjection.ProgressInjectionListener {

    private val name = "Name"
    private val phone = "Phone"
    private val message = "Message"

    private lateinit var progressInjection: ProgressInjection

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        progressInjection = ProgressInjection(this, ctx, view as ViewGroup, R.layout.fragment_profile)
        checkDataExist()
        profileHead.initialize(imgProfile, R.color.tabBack, R.color.colorPrimaryDark)
        setImage()
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
                false,
                phone
            ).defaultChose(false)

        }
        btnAboutMyProfile.setOnClickListener {
            DialogAboutMe()
        }
        btnFriendListMyProfile.setOnClickListener {
            startActivity(Intent(ctx, ActivityFriend::class.java))
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
            override fun studentData(ok: Boolean, message: String, data: StudentProfileData?) {
                if (ok) {
                    progressInjection.cancel()
                    val phone = UserDb().getUserPhone()
                    txtMyStudentId.text = data?.studentId ?: "!"
                    txtMyName.text = data?.name ?: "!"
                    txtMyPhone.text = phone

                } else
                    progressInjection.error(message)
            }
        }).getStudent()
    }

    private fun setData() {
        val sId = StudentProfileDb().getStudentId()
        val name = StudentProfileDb().getStudentName()
        val phone = UserDb().getUserPhone()

        txtMyStudentId.text = sId
        txtMyName.text = name
        txtMyPhone.text = phone
    }

    private fun setImage() {
        StudentProfilePresenter(object : StudentProfilePresenter.StudentProfileResult {
            override fun studentImgData(data: ByteArray?) {
                Glide.with(ctx)
                    .load(data)
                    .into(imgProfile)
            }
        }).downStudentImg()
    }

    fun changeImg(b: Bitmap) {
        val file = AbsoluteFunctions().convertBitMapToFile(b, ctx)
        StudentProfilePresenter(object : StudentProfilePresenter.StudentProfileResult {
            override fun upStudentImgRes(ok: Boolean) {
                if (ok)
                    setImage()
            }
        }).upStudentImg(file)
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