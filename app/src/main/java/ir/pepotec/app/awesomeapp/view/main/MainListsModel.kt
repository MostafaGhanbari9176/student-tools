package ir.pepotec.app.awesomeapp.view.main

import androidx.annotation.DrawableRes
import androidx.annotation.Keep

@Keep
data class MainListsModel(
    val sub:String,
    @DrawableRes val imgId:Int
)