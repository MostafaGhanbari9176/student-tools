package ir.pepotec.app.awesomeapp.view.account

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.auth.GoogleAuthUtil
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.presenter.student.StudentProfilePresenter
import ir.pepotec.app.awesomeapp.view.account.studentField.DialogField
import ir.pepotec.app.awesomeapp.view.main.ActivityMain
import ir.pepotec.app.awesomeapp.view.uses.DialogProgress
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_complete_account.*
import java.util.regex.Pattern
import com.google.android.gms.common.AccountPicker

class FragmentGetStudentData : MyFragment() {

    private var fieldId = -1
    var emailAddress = ""
        set(value) {
            btnChooseEmail.text = value
            field = value
        }

    private val progress = DialogProgress { checkData() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_complete_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        btnNextCompleteAccount.setOnClickListener {
            checkData()
        }
        btnChooseField.setOnClickListener {
            DialogField { fieldId, fieldName ->
                this@FragmentGetStudentData.fieldId = fieldId
                btnChooseField.text = fieldName
            }
        }
        btnChooseEmail.setOnClickListener {
            choseEmail()
        }
    }

    private fun choseEmail() {
        val googlePicker = AccountPicker
            .newChooseAccountIntent(
                null, null, arrayOf(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE),
                true, null, null, null, null
            )
        (ctx as ActivityAccount).startActivityForResult(googlePicker, 1)
    }

    private fun checkData() {
        var text = txtGetStudentId.text.toString().trim()
        if (text.length != 7 || text[6] != '3') {
            txtGetStudentId.apply {
                requestFocus()
                setError("لطفا شماره دانشجویی را صحیح وارد کنید")
            }
            return
        }
        text = txtGetName.text.toString().trim()
        val pattern = Pattern.compile("[\\[\$&+,:;=\\\\?@#|/'<>.^*()%!-\\]]")
        if (text.isEmpty() || pattern.matcher(text).find()) {
            txtGetName.apply {
                requestFocus()
                setError(if (text.length == 0) "لطفا پر کنید" else "فقط حروف الفبا,اعداد و خط تیره مجاز می باشد")
            }
            return
        }
        if (emailAddress.isEmpty()) {
            btnChooseEmail.apply {
                requestFocus()
                setError("لطفا ایمیل خودرا وارد کنید")
            }
            return
        }
        if (fieldId == -1) {
            btnChooseField.apply {
                requestFocus()
                setError("لطفا رشته خودرا انتخاب کنید")
            }
            return
        }
        text = txtGetPass.text.toString().trim()
        if (text.length == 0 || !TextUtils.isDigitsOnly(text)) {
            txtGetPass.apply {
                requestFocus()
                setError(if (text.length == 0) "لطفا پر کنید" else "فقط عدد مجاز می باشد")
            }
            return
        }
        text = txtGetPassAgain.text.toString().trim()
        if (text.length == 0 || !TextUtils.isDigitsOnly(text)) {
            txtGetPassAgain.apply {
                requestFocus()
                setError(if (text.length == 0) "لطفا پر کنید" else "فقط عدد مجاز می باشد")
            }
            return
        }
        val pass = txtGetPass.text.toString().trim()
        val passAgain = txtGetPassAgain.text.toString().trim()
        if (!pass.equals(passAgain)) {
            txtGetPass.apply {
                setError("این دو مقدار باید یکسان باشند")
            }
            txtGetPassAgain.apply {
                requestFocus()
                setError("این دو مقدار باید یکسان باشند")
            }
            return
        }

        saveData()
    }

    private fun saveData() {
        progress.show()
        val name = txtGetName.text.toString().trim()
        val sId = txtGetStudentId.text.toString().trim()
        val pass = txtGetPass.text.toString().trim()

        StudentProfilePresenter(object : StudentProfilePresenter.StudentProfileResult {
            override fun addStudentRes(ok: Boolean, message: String) {
                if (ok) {
                    progress.cancel()
                    startActivity(Intent(ctx, ActivityMain::class.java))
                    (ctx as ActivityAccount).finish()
                } else
                    progress.error(message)
            }
        }).addStudent(sId, name, pass, emailAddress, fieldId)

    }
}