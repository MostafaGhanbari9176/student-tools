package ir.pepotec.app.awesomeapp.presenter

import ir.pepotec.app.awesomeapp.model.ServerRes
import ir.pepotec.app.awesomeapp.model.user.User
import ir.pepotec.app.awesomeapp.model.user.UserData
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent
import com.google.gson.Gson
import ir.pepotec.app.awesomeapp.model.ServerResConst
import ir.pepotec.app.awesomeapp.model.user.UserDb
import ir.pepotec.app.awesomeapp.view.uses.App


class UserPresenter(private val listener: UserResult) : User.UserResponse {


    interface UserResult {
        fun resultFromUser(ok: Boolean, message: String)
    }

    fun createVerifyCode(phone: String) {
        UserDb().savePhone(phone)
        (User(this)).createVerifyCode(phone)
    }

    fun checkVerifyCode(verifyCode: Int) {
        val phone = UserDb().getUserPhone()
        (User(this)).checkVerifyCode(phone, verifyCode)
    }

    fun logIn(phone: String, pass: String) {
        (User(this)).logIn(phone, pass)
    }

    fun newPass(pass: String) {
        val phone = UserDb().getUserPhone()
        (User(this)).newPass(phone, pass)
    }

    override fun createVerifyCodeRes(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.resultFromUser(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!")
            ServerResConst.error -> listener.resultFromUser(false, "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!")
            ServerResConst.ok -> listener.resultFromUser(true, "")
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun checkVerifyCodeRes(res: ServerRes?) {
        val code = res?.code ?: -1
        when (code) {
            -1 -> listener.resultFromUser(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!")
            ServerResConst.error -> listener.resultFromUser(false, "کد وارد شده صحیح نمی باشد!")
            ServerResConst.ok -> listener.resultFromUser(true, "")
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun logInRes(res: ServerRes?) {
        val code = res?.code ?: -1
        when (code) {
            -1 -> listener.resultFromUser(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!")
            ServerResConst.error -> listener.resultFromUser(false, "رمزعبور وارد شده صحیح نمی باشد!")
            ServerResConst.ok -> {
                val json = (res?.data)?.getJSONObject(0).toString()
                val data = Gson().fromJson(json, UserData::class.java)
                UserDb().saveUserData(data)
                listener.resultFromUser(true, "")
            }
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun newPassRes(res: ServerRes?) {
        val code = res?.code ?: -1
        when (res?.code ?: -1) {
            -1 -> listener.resultFromUser(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!")
            ServerResConst.error -> listener.resultFromUser(false, "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!")
            ServerResConst.ok -> {
                val json = (res?.data)?.getJSONObject(0).toString()
                val data = Gson().fromJson(json, UserData::class.java)
                UserDb().saveUserData(data)
                listener.resultFromUser(true, "")
            }
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun logOut(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.resultFromUser(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!")
            ServerResConst.error -> listener.resultFromUser(false, "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!")
            ServerResConst.ok -> {
                UserDb().removeData()
                listener.resultFromUser(true, "")
            }
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun userError(message: String?) {
        listener.resultFromUser(false, "خطایی رخ داده لطفا اتصال اینترنت خود را چک کرده و مجددا تلاش کنید!")
    }
}