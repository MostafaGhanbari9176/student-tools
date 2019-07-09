package ir.pepotec.app.awesomeapp.view.student.profile

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.VectorDrawable
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.uses.App
import kotlinx.android.synthetic.main.dialog_eye_level.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DialogEyeLevel(
    private var messageAll: String,
    private var messageFriend: String,
    private val couple: EyeLevelCouple,
    private val info:Boolean,
    private val flag:String,
    ctx: Context = App.instanse
) :
    Dialog(ctx) {
    interface EyeLevelCouple {
        fun choseResult(All: Boolean)
    }
    private lateinit var v:View
    init {
        v = LayoutInflater.from(ctx).inflate(R.layout.dialog_eye_level, null, false)
        setContentView(v)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        show()
        setUp()
    }

    private fun setUp() {
        txtAllDialogEL.apply {
            text = messageAll
            setOnClickListener {
                changeCheck(true)
                couple.choseResult(true)
            }
        }
        txtFriendDialogEL.apply {
            text = messageFriend
            setOnClickListener {
                changeCheck(false)
                couple.choseResult(false)
            }
        }
        btnCloseDialogEL.setOnClickListener {
            this@DialogEyeLevel.cancel()
        }
    }

    fun defaultChose(All: Boolean) {
        PBEL.visibility = View.GONE
        txtAllDialogEL.visibility = View.VISIBLE
        txtFriendDialogEL.visibility = View.VISIBLE
        val h = v.layoutParams.width
        changeCheck(All)
    }

    private fun changeCheck(All: Boolean) {
        txtInfoDialogEL.visibility = if(!All && info) View.VISIBLE else View.GONE

        txtAllDialogEL.setCompoundDrawablesWithIntrinsicBounds(
            ContextCompat.getDrawable(
                context,
                if (All)
                    R.drawable.ic_done_animate
                else
                    R.drawable.ic_un_done_animate
            ), null, null, null
        )
        txtFriendDialogEL.setCompoundDrawablesWithIntrinsicBounds(
            ContextCompat.getDrawable(
                context,
                if (All)
                    R.drawable.ic_un_done_animate
                else
                    R.drawable.ic_done_animate
            ), null, null, null
        )
        (txtAllDialogEL.compoundDrawables[0] as AnimatedVectorDrawable).start()
        (txtFriendDialogEL.compoundDrawables[0] as AnimatedVectorDrawable).start()
        val h = Handler()
        Thread(
            Runnable {
                Thread.sleep(560)
                h.post {
                    txtAllDialogEL.setCompoundDrawablesWithIntrinsicBounds(
                        if(All){
                            ContextCompat.getDrawable(context, R.drawable.ic_done)}
                        else{
                            null
                        },
                        null, null, null
                    )
                    txtFriendDialogEL.setCompoundDrawablesWithIntrinsicBounds(
                        if(!All){ ContextCompat.getDrawable(context, R.drawable.ic_done)}
                        else{ null},
                        null, null, null
                    )
                }
            }
        ).start()
    }
}