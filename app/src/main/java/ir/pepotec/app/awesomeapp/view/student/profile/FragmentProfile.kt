package ir.pepotec.app.awesomeapp.view.student.profile

import android.animation.ObjectAnimator
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.student.ActivityStudent
import ir.pepotec.app.awesomeapp.view.student.profile.friendList.ActivityFriend
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.activity_student.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class FragmentProfile : MyFragment(), DialogEyeLevel.EyeLevelCouple {

    private val name = "Name"
    private val phone = "Phone"
    private val message = "Message"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        profileHead.initialize(imgProfile, R.color.tabBack)
        animateParent()
        txtMyName.setOnClickListener {
            showDialog()
        }
        txtMyPhone.setOnClickListener {
            DialogEyeLevel(getString(R.string.allEyeLevel), getString(R.string.friendEyeLevel), this, false, phone).defaultChose(false)

        }
        btnMessageForMi.setOnClickListener {
            DialogEyeLevel(
                getString(R.string.AlowAllSendMessage),
                getString(R.string.AlowFriendSendMessage),
                this,
                true,
                message
            ).defaultChose(true)
        }
        btnAboutMyProfile.setOnClickListener {
            showDialogAboutMe()
        }

        txtSocilaListMyProfile.setOnClickListener {
            val d = Dialog(ctx)
            val v = LayoutInflater.from(ctx).inflate(R.layout.dialog_social_link, null, false)
            d.setContentView(v)
            d.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            d.show()
        }

        btnFriendListMyProfile.setOnClickListener {
            startActivity(Intent(ctx, ActivityFriend::class.java))
        }
    }

    private fun showDialogAboutMe() {
        val d = Dialog(ctx)
        val v = LayoutInflater.from(ctx).inflate(R.layout.dialog_get_user_text, null, false)
        d.setContentView(v)
        d.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        d.show()
    }

    private fun showDialog() {
        val d = DialogEyeLevel("نمایش برای همه", "نمایش برای دوستان", this, false, name)
        doAsync {
            Thread.sleep(1000)
            uiThread {
                d.defaultChose(true)
            }
        }
    }

    override fun choseResult(All: Boolean) {
        toast("$All")
    }

    private fun animateParent() {
        ObjectAnimator.ofFloat(profileViewParent, View.ALPHA, 0f, 1f).apply {
            startDelay = 350
            duration = 250
            interpolator = AccelerateInterpolator()
            start()
        }
    }

}