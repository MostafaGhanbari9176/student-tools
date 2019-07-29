package ir.pepotec.app.awesomeapp.view.student.profile

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.presenter.student.StudentProfilePresenter
import ir.pepotec.app.awesomeapp.view.uses.App
import kotlinx.android.synthetic.main.dialog_get_user_text.*

class DialogAboutMe:Dialog(App.instanse) {

    init {
        val v = LayoutInflater.from(context).inflate(R.layout.dialog_get_user_text, null, false)
        setContentView(v)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        show()
        setUp()
        getAboutMeData()
    }

    private fun getAboutMeData() {
        StudentProfilePresenter(object :StudentProfilePresenter.StudentProfileResult{
            override fun aboutMeData(ok: Boolean, message: String, data: String?) {
                if(ok)
                    setDefData(data!!)
            }
        }).getAboutMe()
    }

    private fun setUp() {
        btnSaveAboutMe.setOnClickListener {
            saveData()
        }
        btnCancellAboutMe.setOnClickListener {
            cancel()
        }
    }

    private fun saveData() {
        val data = txtAboutMe.text.toString().trim()
        StudentProfilePresenter(object :StudentProfilePresenter.StudentProfileResult{
            override fun saveAboutMeRes(ok: Boolean, message: String) {
                super.saveAboutMeRes(ok, message)
            }
        }).saveAboutMe(data)
        cancel()
    }

    private fun setDefData(text:String)
    {
        LLBtnAboutMe.visibility = View.VISIBLE
        txtAboutMe.visibility = View.VISIBLE
        pBarAboutMe.visibility = View.GONE
        txtAboutMe.setText(text)
    }

}