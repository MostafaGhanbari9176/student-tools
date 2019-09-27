package ir.pepotec.app.awesomeapp.view.uses

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import ir.pepotec.app.awesomeapp.R
import kotlinx.android.synthetic.main.dialog_progress.*
import kotlinx.android.synthetic.main.dialog_progress.view.*
import org.jetbrains.anko.toast

class DialogProgress(ctx: Context = App.instanse, private val listener:()->Unit) : Dialog(ctx) {

    init {
        val v = LayoutInflater.from(ctx).inflate(R.layout.dialog_progress, null, false)
        v.btnTryAgainProgress.setOnClickListener {
            listener()
        }
        v.btnTBackProgress.setOnClickListener {
            cancel()
            (App.instanse as MyActivity).onBackPressed()
        }
        setContentView(v)
        window?.apply {
            setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            setGravity(Gravity.CENTER)
            setBackgroundDrawable(ContextCompat.getDrawable(ctx, R.drawable.progress_back))
        }
        setCancelable(false)
    }

    override fun show() {
        super.show()
        btnTryAgainProgress.visibility = View.GONE
        btnTBackProgress.visibility = View.GONE
        (imgDialogProgress.drawable as AnimatedVectorDrawable).start()
    }

    fun error(message:String)
    {
        context.toast(message)
        (imgDialogProgress.drawable as AnimatedVectorDrawable).stop()
        btnTryAgainProgress.visibility = View.VISIBLE
        btnTBackProgress.visibility = View.VISIBLE
    }

}