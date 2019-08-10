package ir.pepotec.app.awesomeapp.model

data class ServerRes(val code:Int, val message:String, val data:ArrayList<String>)

class ServerResConst()
{
    companion object {
        val ok = 100
        val error = 200
        val badLogSign = 300
        val apiCodeError = 400
    }
}