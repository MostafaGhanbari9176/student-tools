package ir.pepotec.app.awesomeapp.model.user

import androidx.annotation.Keep

@Keep
data class UserData(
    val phone: String,
    val api_code: String,
    val kind: Int
)

class UserKind
{
    companion object
    {
        const val student:Int = 1
    }
}

