package ir.pepotec.app.awesomeapp.presenter

import ir.pepotec.app.awesomeapp.model.ServerRes
import ir.pepotec.app.awesomeapp.model.user.User
import ir.pepotec.app.awesomeapp.model.user.UserData
import com.google.gson.Gson
import ir.pepotec.app.awesomeapp.model.ServerResConst
import ir.pepotec.app.awesomeapp.model.user.UserDb
import ir.pepotec.app.awesomeapp.model.user.UserKind
import ir.pepotec.app.awesomeapp.view.uses.App


class UserPresenter(private val listener: UserResult) : User.UserResponse {

    interface UserResult {
        fun resultFromUser(ok: Boolean, message: String)
        fun badLogIn(){}
        fun badSignUp(){}
    }

    fun createVerifyCode(phone: String) {
        UserDb().savePhone(phone)
        (User(this)).sendVerifyCode(phone)
    }

    fun signUp(verifyCode: String) {
        val phone = UserDb().getUserPhone()
        (User(this)).signUp(phone, verifyCode, UserKind.student)
    }

    fun logIn(phone: String, pass: String) {
        UserDb().savePhone(phone)
        (User(this)).logIn(phone, pass)
    }

    fun newPass(verifyCode: String) {
        val phone = UserDb().getUserPhone()
        (User(this)).newPass(phone, verifyCode)
    }

    fun changePass(pass: String) {
        val phone = UserDb().getUserPhone()
        val ac = UserDb().getUserApiCode()
        (User(this)).changePass(phone, pass, ac)
    }

    override fun createVerifyCodeRes(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.resultFromUser(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!")
            ServerResConst.error -> listener.resultFromUser(false, "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!")
            ServerResConst.ok -> listener.resultFromUser(true, "")
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun signUpRes(res: ServerRes?) {
        val code = res?.code ?: -1
        when (code) {
            -1 -> listener.resultFromUser(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!")
            ServerResConst.error -> listener.resultFromUser(false, "کد وارد شده صحیح نمی باشد!")
            ServerResConst.badLogSign -> listener.badSignUp()
            ServerResConst.ok -> {
                val data = res!!.data[0]
                UserDb().saveApiCode(data)
                listener.resultFromUser(true, "")
            }
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun logInRes(res: ServerRes?) {
        val code = res?.code ?: -1
        when (code) {
            -1 -> listener.resultFromUser(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!")
            ServerResConst.error -> listener.resultFromUser(false, "رمزعبور وارد شده صحیح نمی باشد!")
            ServerResConst.badLogSign -> listener.badLogIn()
            ServerResConst.ok -> {
                val json = res!!.data[0]
                val data = Gson().fromJson(json, UserData::class.java)
                UserDb().saveUserData(data)
                listener.resultFromUser(true, "")
            }
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }

    override fun newPassRes(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.resultFromUser(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!")
            ServerResConst.error -> listener.resultFromUser(false, "کد وارد شده صحیح نمی باشد!")
            ServerResConst.badLogSign -> listener.badLogIn()
            ServerResConst.ok -> {
                val json = res!!.data[0]
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

    override fun changePassRes(res: ServerRes?) {
        when (res?.code ?: -1) {
            -1 -> listener.resultFromUser(false, "خطایی رخ داده لطفا اتصال اینترنت خودرا چک کرده و مجددا تلاش کنید!")
            ServerResConst.error -> listener.resultFromUser(false, "با عرض پوزش خطایی رخ داده لطفا بعدا امتحان کنید!")
            ServerResConst.ok -> listener.resultFromUser(true, "")
            ServerResConst.apiCodeError -> App.apiCodeError()
        }
    }
}