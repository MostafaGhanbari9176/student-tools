package ir.pepotec.app.awesomeapp.view.uses

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import ir.pepotec.app.awesomeapp.R
import kotlinx.android.synthetic.main.view_progress_injection.view.*

class ProgressInjection(private val listener:ProgressInjectionListener, private val ctx: Context, private val parentView:ViewGroup,@LayoutRes private val orgView:Int) {

    interface ProgressInjectionListener
    {
        fun pressTryAgain()
    }

    private var view: View = LayoutInflater.from(ctx).inflate(R.layout.view_progress_injection, parentView, false)

    fun show()
    {
        parentView.removeAllViews()
        parentView.addView(view)
        view.apply {
            background = ContextCompat.getDrawable(ctx, R.drawable.dark_back_0a5)
            imgProgressInjection.visibility = View.VISIBLE
            LLProgressInjection.visibility = View.GONE
            (imgProgressInjection.drawable as AnimatedVectorDrawable).start()
        }
    }

    fun cancel()
    {
        parentView.removeAllViews()
        parentView.addView(LayoutInflater.from(ctx).inflate(orgView, parentView, false))
    }

    fun error(message: String) {
        view.apply {
            background = ColorDrawable(ContextCompat.getColor(ctx, R.color.error))
            imgProgressInjection.visibility = View.GONE
            LLProgressInjection.visibility = View.VISIBLE
            txtProgressInjection.text = message
            btnProgressInjection.setOnClickListener {
                listener.pressTryAgain()
            }
        }
    }

}