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
import kotlinx.android.synthetic.main.fragment_new_pass.*

class FragmentNewPass : MyFragment(), UserPresenter.UserResult {

    val progress = DialogProgress{checkData()}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_pass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        btnSubmitNewPass.setOnClickListener {
            checkData()
        }
    }

    private fun checkData() {
        var text = txtNewPass.text.toString()
        if (text.length == 0 || !TextUtils.isDigitsOnly(text)) {
            txtNewPass.apply {
                requestFocus()
                setError(if (text.length == 0) "لطفا پر کنید" else "فقط عدد مجاز می باشد")
            }
            return
        }
        text = txtNewPassAgain.text.toString()
        if (text.length == 0 || !TextUtils.isDigitsOnly(text)) {
            txtNewPassAgain.apply {
                requestFocus()
                setError(if (text.length == 0) "لطفا پر کنید" else "فقط عدد مجاز می باشد")
            }
            return
        }
        val pass = txtNewPass.text.toString().trim()
        val passAgain = txtNewPassAgain.text.toString().trim()
        if (!pass.equals(passAgain)) {
            txtNewPass.apply {
                setError("این دو مقدار باید یکسان باشند")
            }
            txtNewPassAgain.apply {
                requestFocus()
                setError("این دو مقدار باید یکسان باشند")
            }
        }

        sendPassToServer(pass)
    }

    private fun sendPassToServer(pass: String) {
        progress.show()
        UserPresenter(this).changePass(pass)
    }

    override fun resultFromUser(ok: Boolean, message: String) {
        if (ok) {
        progress.cancel()
            startActivity(Intent(ctx, ActivityMain::class.java))
            (ctx as ActivityAccount).finish()
        }else
            progress.error(message)
    }

}