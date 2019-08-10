package ir.pepotec.app.awesomeapp.view.account

import android.content.DialogInterface
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.uses.App
import ir.pepotec.app.awesomeapp.view.uses.MyActivity

class ActivityAccount : MyActivity(R.id.ContentAccount) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
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
}