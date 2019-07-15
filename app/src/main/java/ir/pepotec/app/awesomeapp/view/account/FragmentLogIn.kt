package ir.pepotec.app.awesomeapp.view.account

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.main.ActivityMain
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_log_in.*

class FragmentLogIn:MyFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_log_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        btnLogIn.requestFocus()
        btnLogIn.setOnClickListener {
            checkData()
        }
        btnForgetPass.setOnClickListener {
            val f = FragmentGetPhone()
            f.signUp = false
            (ctx as ActivityAccount).changeView(f)
        }
    }

    private fun checkData() {
    var text = txtStudentIdLogIn.text.toString()
        if(text.length != 7 || text[6] != '3')
        {
            txtStudentIdLogIn.apply {
                requestFocus()
                setError("لطفا شماره دانشجویی را صحیح وارد کنید")
            }
            return
        }
        text = txtPassLogIn.text.toString()
        if (text.length == 0 || !TextUtils.isDigitsOnly(text)) {
            txtPassLogIn.apply {
                requestFocus()
                setError(if (text.length == 0) "لطفا پر کنید" else "فقط عدد مجاز می باشد")
            }
            return
        }
        startActivity(Intent(ctx, ActivityMain::class.java))
        (ctx as ActivityAccount).finish()
    }
}