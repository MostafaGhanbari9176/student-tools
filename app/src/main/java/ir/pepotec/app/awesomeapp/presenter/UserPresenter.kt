package ir.pepotec.app.awesomeapp.presenter

import ir.pepotec.app.awesomeapp.model.ServerRes
import ir.pepotec.app.awesomeapp.model.user.User
import ir.pepotec.app.awesomeapp.model.user.UserData
import com.google.gson.Gson
import ir.pepotec.app.awesomeapp.model.student.profile.StudentProfileDb
import ir.pepotec.app.awesomeapp.model.user.UserDb
import ir.pepotec.app.awesomeapp.model.user.UserKind
import ir.pepotec.app.awesomeapp.view.uses.AF
import ir.pepotec.app.awesomeapp.view.uses.App


class UserPresenter(private val listener: UserResult) : User.UserResponse {

    interface UserResult {
        fun resultFromUser(ok: Boolean, message: String)
        fun badLogIn() {}
        fun badSignUp() {}
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
        listener.resultFromUser(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1))
    }

    override fun signUpRes(res: ServerRes?) {
        res?.let {
            UserDb().saveApiCode(it.data[0])
        }
        listener.resultFromUser(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1))
    }

    override fun logInRes(res: ServerRes?) {

        if (res?.code == ServerRes.ok) {

            val data = Gson().fromJson(res.data[0], UserData::class.java)
            UserDb().saveUserData(data)

            StudentProfileDb().isLogIn = true
        }
        listener.resultFromUser(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1))

    }

    override fun newPassRes(res: ServerRes?) {
        res?.let {
            val data = Gson().fromJson(it.data[0], UserData::class.java)
            UserDb().saveUserData(data)
        }
        listener.resultFromUser(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1))
    }

    override fun logOut(res: ServerRes?) {
        UserDb().removeData()
        listener.resultFromUser(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1))
    }

    override fun changePassRes(res: ServerRes?) {
        listener.resultFromUser(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1))
    }
}