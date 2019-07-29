package ir.pepotec.app.awesomeapp.model

import org.json.JSONArray

data class ServerRes(val code:Int, val message:String, val data:JSONArray)

class ServerResConst()
{
    companion object {
        val ok = 100
        val error = 200
        val apiCodeError = 300
    }
}