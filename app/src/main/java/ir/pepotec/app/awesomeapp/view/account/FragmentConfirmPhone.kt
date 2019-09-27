package ir.pepotec.app.awesomeapp.view.account

import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.dialog.MaterialDialogs
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.user.UserDb
import ir.pepotec.app.awesomeapp.presenter.UserPresenter
import ir.pepotec.app.awesomeapp.view.uses.DialogProgress
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_confirm_phone.*

class FragmentConfirmPhone : MyFragment(), UserPresenter.UserResult {

    var signUp = true
    val progress = DialogProgress { checkData() }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_confirm_phone, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        txtInfoConfirmPhone.text = "کد تایید لحضاتی پیش به شماره همراه ${UserDb().getUserPhone()} ارسال شده است"
        btnNextConfPhone.requestFocus()
        btnNextConfPhone.setOnClickListener {
            checkData()
        }
    }

    private fun checkData() {
        val v = txtGetConfCode.text.toString().trim()
        if (v.length == 6 && TextUtils.isDigitsOnly(v)) {
            sendCodeToServer(v)
        } else
            txtGetConfCode.apply {
                requestFocus()
                setError("کد تایید شش رقم بوده و فقط شامل عدد می باشد")
            }

    }

    private fun sendCodeToServer(v: String) {
        progress.show()
        UserPresenter(this).apply {
            if (signUp)
                signUp(v)
            else
                newPass(v)
        }
    }

    override fun resultFromUser(ok: Boolean, message: String) {
        if (ok) {
            progress.cancel()
            (ctx as ActivityAccount).changeView(if (signUp) FragmentGetStudentData() else FragmentNewPass())
        } else
            progress.error(message)
    }

    override fun badSignUp() {
        (ctx as ActivityAccount).badSignUp()
    }

    override fun badLogIn() {
        (ctx as ActivityAccount).badLogIn()
    }

}