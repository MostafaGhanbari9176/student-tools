package ir.pepotec.app.awesomeapp.view.account

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_confirm_phone.*

class FragmentConfirmPhone:MyFragment() {

    var signUp = true

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
        val v = txtGetConfCode.text.toString()
        if (v.length == 6 && TextUtils.isDigitsOnly(v)) {
            (ctx as ActivityAccount).changeView(if (signUp) FragmentCompleteAccount() else FragmentNewPass())
        } else
            txtGetConfCode.apply {
                requestFocus()
                setError("کد تایید شش رقم بوده و فقط شامل عدد می باشد")
            }

    }

}