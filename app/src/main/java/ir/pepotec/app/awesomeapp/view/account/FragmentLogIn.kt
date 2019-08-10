package ir.pepotec.app.awesomeapp.view.account

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.presenter.UserPresenter
import ir.pepotec.app.awesomeapp.view.main.ActivityMain
import ir.pepotec.app.awesomeapp.view.uses.DialogProgress
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_log_in.*

class FragmentLogIn : MyFragment(), UserPresenter.UserResult {

    val progress = DialogProgress()

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
        var phone = txtPhoneLogIn.text.toString()
        if (phone.length != 11 || phone[0] != '0') {
            txtPhoneLogIn.apply {
                requestFocus()
                error = "لطفا شماره همراه خود را صحیح وارد کنید"
            }
            return
        }
        val pass = txtPassLogIn.text.toString()
        if (pass.isEmpty() || !TextUtils.isDigitsOnly(pass)) {
            txtPassLogIn.apply {
                requestFocus()
                error = if (pass.isEmpty()) "لطفا پر کنید" else "فقط عدد مجاز می باشد"
            }
            return
        }
        sendDataToServer(phone, pass)
    }

    private fun sendDataToServer(phone: String, pass: String) {
        progress.show()
        UserPresenter(this).logIn(phone, pass)
    }

    override fun resultFromUser(ok: Boolean, message: String) {
        progress.cancel()
        if (ok) {
            startActivity(Intent(ctx, ActivityMain::class.java))
            (ctx as ActivityAccount).finish()
        } else
            toast(message)
    }

    override fun badLogIn() {
        (ctx as ActivityAccount).badLogIn()
    }

}