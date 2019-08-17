package ir.pepotec.app.awesomeapp.view.student.profile

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.uses.App
import kotlinx.android.synthetic.main.dialog_profile_img.*
import androidx.core.app.ActivityCompat.startActivityForResult
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Handler
import android.view.View
import androidx.core.content.ContextCompat
import ir.pepotec.app.awesomeapp.model.student.profile.StudentEyeLevel
import ir.pepotec.app.awesomeapp.presenter.student.StudentProfilePresenter
import ir.pepotec.app.awesomeapp.view.student.ActivityStudent

class DialogProfileImg(private val ctx :Context = App.instanse): Dialog(ctx) {

    init {
        val v = LayoutInflater.from(ctx).inflate(R.layout.dialog_profile_img, null, false)
        setContentView(v)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        show()
        setUp()
        getDefData()
    }

    private fun getDefData() {
        StudentProfilePresenter(object:StudentProfilePresenter.StudentProfileResult{
            override fun elImgRes(ok: Boolean, message: String, data: Int?) {
                if(ok)
                    defaultChose(data == StudentEyeLevel.allUser)
            }
        }).eLImg(-1)
    }

    private fun setUp() {
        btnChoseProfileImg.setOnClickListener {
            choseImg()
            cancel()
        }
        txtAllProfileImg.setOnClickListener {
            changeCheck(true)
            changeEyeLevel(StudentEyeLevel.allUser)
        }
        txtFriendProfileImg.setOnClickListener {
            changeCheck(false)
            changeEyeLevel(StudentEyeLevel.justFriends)
        }
    }

    private fun changeEyeLevel(code: Int) {
        StudentProfilePresenter(object:StudentProfilePresenter.StudentProfileResult{
            override fun elImgRes(ok: Boolean, message: String, data: Int?) {
                super.elImgRes(ok, message, data)
            }
        }).eLImg(code)
    }

    fun defaultChose(All: Boolean) {
        pBarImgEL.visibility = View.GONE
        txtFriendProfileImg.visibility = View.VISIBLE
        txtAllProfileImg.visibility = View.VISIBLE
        changeCheck(All)
    }

    private fun choseImg() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(ctx as ActivityStudent,Intent.createChooser(intent, "Select Picture"), 1, null)
    }

    private fun changeCheck(All: Boolean) {
        txtAllProfileImg.setCompoundDrawablesWithIntrinsicBounds(
            ContextCompat.getDrawable(
                context,
                if (All)
                    R.drawable.ic_done_animate
                else
                    R.drawable.ic_un_done_animate
            ), null, null, null
        )
        txtFriendProfileImg.setCompoundDrawablesWithIntrinsicBounds(
            ContextCompat.getDrawable(
                context,
                if (All)
                    R.drawable.ic_un_done_animate
                else
                    R.drawable.ic_done_animate
            ), null, null, null
        )
        (txtAllProfileImg.compoundDrawables[0] as AnimatedVectorDrawable).start()
        (txtFriendProfileImg.compoundDrawables[0] as AnimatedVectorDrawable).start()
        val h = Handler()
        Thread(
            Runnable {
                Thread.sleep(560)
                h.post {
                    txtAllProfileImg.setCompoundDrawablesWithIntrinsicBounds(
                        if(All){
                            ContextCompat.getDrawable(context, R.drawable.ic_done)}
                        else{
                            null
                        },
                        null, null, null
                    )
                    txtFriendProfileImg.setCompoundDrawablesWithIntrinsicBounds(
                        if(!All){ ContextCompat.getDrawable(context, R.drawable.ic_done)}
                        else{ null},
                        null, null, null
                    )
                }
            }
        ).start()
    }

}