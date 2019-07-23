package ir.pepotec.app.awesomeapp.presenter

import ir.pepotec.app.awesomeapp.model.Pref
import ir.pepotec.app.awesomeapp.model.ServerRes
import ir.pepotec.app.awesomeapp.model.user.User
import ir.pepotec.app.awesomeapp.model.user.UserData
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent
import com.google.gson.Gson
import ir.pepotec.app.awesomeapp.model.user.UserDb
import ir.pepotec.app.awesomeapp.view.uses.App


class UserPresenter(private val listener: UserPresenterListener) : User.UserListener {


    interface UserPresenterListener {
        fun resultFromUser(ok: Boolean, message: String)
    }

    fun createVerifyCode(phone: String) {
        Pref().saveStringValue(Pref.phone, phone)
        (User(this)).createVerifyCode(phone)
    }

    fun checkVerifyCode(verifyCode: Int) {
        val phone = Pref().getStringValue(Pref.phone, "")
        (User(this)).checkVerifyCode(phone, verifyCode)
    }

    fun logIn(phone: String, pass: String) {
        (User(this)).logIn(phone, pass)
    }

    fun newPass(pass: String) {
        val phone = Pref().getStringValue(Pref.phone, "")
        (User(this)).newPass(phone, pass)
    }

    override fun createVerifyCodeRes(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.resultFromUser(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!")
            0 -> listener.resultFromUser(false, "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!")
            1 -> listener.resultFromUser(true, "")
            2 -> {
                val homeIntent = Intent(Intent.ACTION_MAIN)
                homeIntent.addCategory(Intent.CATEGORY_HOME)
                homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(App.instanse, homeIntent, null)
                listener.resultFromUser(false, "")
            }
        }
    }

    override fun checkVerifyCodeRes(res: ServerRes?) {
        val code = res?.code ?: -1
        when (code) {
            -1 -> listener.resultFromUser(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!")
            0 -> listener.resultFromUser(false, "کد وارد شده صحیح نمی باشد!")
            1 -> listener.resultFromUser(true, "")
            2 -> {
            }
        }
    }

    override fun logInRes(res: ServerRes?) {
        val code = res?.code ?: -1
        when (code) {
            -1 -> listener.resultFromUser(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!")
            0 -> listener.resultFromUser(false, "رمزعبور وارد شده صحیح نمی باشد!")
            1 -> {
                val json = (res?.data)?.getJSONObject(0).toString()
                val data = Gson().fromJson(json, UserData::class.java)
                UserDb().saveUserData(data)
                listener.resultFromUser(true, "")
            }
            2 -> {
            }
        }
    }

    override fun newPassRes(res: ServerRes?) {
        val code = res?.code ?: -1
        when (res?.code ?: -1) {
            -1 -> listener.resultFromUser(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!")
            0 -> listener.resultFromUser(false, "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!")
            1 -> {
                val json = (res?.data)?.getJSONObject(0).toString()
                val data = Gson().fromJson(json, UserData::class.java)
                UserDb().saveUserData(data)
                listener.resultFromUser(true, "")
            }
            2 -> {
                val homeIntent = Intent(Intent.ACTION_MAIN)
                homeIntent.addCategory(Intent.CATEGORY_HOME)
                homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(App.instanse, homeIntent, null)
                listener.resultFromUser(false, "")
            }
        }
    }

    override fun logOut(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.resultFromUser(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!")
            0 -> listener.resultFromUser(false, "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!")
            1 -> {
                UserDb().removeData()
                listener.resultFromUser(true, "")
            }
            2 -> {
                val homeIntent = Intent(Intent.ACTION_MAIN)
                homeIntent.addCategory(Intent.CATEGORY_HOME)
                homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(App.instanse, homeIntent, null)
                listener.resultFromUser(false, "")
            }
        }
    }

    override fun userError(message: String?) {
        listener.resultFromUser(false, "خطایی رخ داده لطفا اتصال اینترنت خود را چک کرده و مجددا تلاش کنید!")
    }
}