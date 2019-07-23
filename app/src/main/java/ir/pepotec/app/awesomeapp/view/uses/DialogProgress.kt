package ir.pepotec.app.awesomeapp.view.uses

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import ir.pepotec.app.awesomeapp.R
import kotlinx.android.synthetic.main.dialog_progress.*

class DialogProgress(ctx: Context = App.instanse) : Dialog(ctx) {
    init {
        val v = LayoutInflater.from(ctx).inflate(R.layout.dialog_progress, null, false)
        setContentView(v)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
    }

    override fun show() {
        super.show()
        (imgDialogProgress.drawable as AnimatedVectorDrawable).start()
    }

}