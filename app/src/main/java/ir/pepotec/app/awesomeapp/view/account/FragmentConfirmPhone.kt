package ir.pepotec.app.awesomeapp.view.account

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.user.UserData
import ir.pepotec.app.awesomeapp.presenter.UserPresenter
import ir.pepotec.app.awesomeapp.view.uses.DialogProgress
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_confirm_phone.*

class FragmentConfirmPhone:MyFragment() ,UserPresenter.UserPresenterListener{

    var signUp = true
    val progress = DialogProgress()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_confirm_phone, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
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
        UserPresenter(this).checkVerifyCode(v.toInt())
    }

    override fun resultFromUser(ok: Boolean, message: String) {
        progress.cancel()
        if(ok) {
            (ctx as ActivityAccount).changeView(if (signUp) FragmentCompleteAccount() else FragmentNewPass())
        }
        else
            toast(message)
    }

}