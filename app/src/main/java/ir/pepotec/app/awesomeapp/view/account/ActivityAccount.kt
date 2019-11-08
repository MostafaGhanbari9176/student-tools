package ir.pepotec.app.awesomeapp.view.account

import android.accounts.AccountManager
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.uses.App
import ir.pepotec.app.awesomeapp.view.uses.MyActivity

class ActivityAccount : MyActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_common)
        App.instanse = this
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        changeView(FragmentLogSign())
    }

    override fun onResume() {
        super.onResume()
        App.instanse = this
    }

    fun firstPage() {
        backHistory.removeAllElements()
        changeView(FragmentLogSign())
    }

    fun badSignUp() {
        MaterialAlertDialogBuilder(this).
            setTitle("خطا")
            .setMessage("شما قبلا حساب کاربری ایجاد کرده اید.")
            .setPositiveButton("ورود به حساب کاربری",
                DialogInterface.OnClickListener { dialog, which -> firstPage() })
            .show()
    }

    fun badLogIn() {
        MaterialAlertDialogBuilder(this).
            setTitle("خطا")
            .setMessage("شما حساب کاربری ندارید.")
            .setPositiveButton("ایجاد حساب کاربری",DialogInterface.OnClickListener { dialog, which -> firstPage() })
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK)
        {
            if(requestCode == 1)
                (backHistory.peek() as FragmentGetStudentData).emailAddress = data?.getStringExtra(AccountManager.KEY_ACCOUNT_NAME) ?: ""
        }
    }

}