package ir.pepotec.app.awesomeapp.model.user

data class UserData(
    val phone: String,
    val apiCode: String,
    val kind: Int
)

class UserKind
{
    companion object
    {
        val student:Int = 1
    }
}

