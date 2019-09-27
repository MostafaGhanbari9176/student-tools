package ir.pepotec.app.awesomeapp.view.account

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.presenter.UserPresenter
import ir.pepotec.app.awesomeapp.view.uses.DialogProgress
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_get_phone.*

class FragmentGetPhone : MyFragment(), UserPresenter.UserResult {

    var signUp = true
    val progress = DialogProgress { checkData() }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_get_phone, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        if (!signUp)
            txtTtlGetPhone.text = "فراموشی رمز عبور"
        btnNextGetPhone.setOnClickListener {
            checkData()
        }
    }

    private fun checkData() {
        val v = txtGetPhone.text.toString().trim()
        if (v.length == 11 && v[0] == '0' && TextUtils.isDigitsOnly(v)) {
            sendPhoneToServer(v)
        } else
            txtGetPhone.apply {
                requestFocus()
                setError("لطفا شماره همراه خود را صحیح وارد کنید")
            }

    }

    private fun sendPhoneToServer(v: String) {
        progress.show()
        (UserPresenter(this)).createVerifyCode(v)
    }

    override fun resultFromUser(ok: Boolean, message: String) {
        if (ok) {
            progress.cancel()
            val f = FragmentConfirmPhone()
            f.signUp = this@FragmentGetPhone.signUp
            (ctx as ActivityAccount).changeView(f)
        } else
            progress.error(message)
    }

}