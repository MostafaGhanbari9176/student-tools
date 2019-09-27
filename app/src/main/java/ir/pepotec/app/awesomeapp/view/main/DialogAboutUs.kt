package ir.pepotec.app.awesomeapp.view.main

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.uses.App

class DialogAboutUs: Dialog(App.instanse) {

    init {
        val v = LayoutInflater.from(context).inflate(R.layout.dialog_us, null, false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        v.setOnClickListener {
            cancel()
        }
        setContentView(v)
    }

}