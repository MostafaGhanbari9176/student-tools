package ir.pepotec.app.awesomeapp.view.file

import android.os.Bundle
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.uses.App
import ir.pepotec.app.awesomeapp.view.uses.MyActivity

class ActivityFile:MyActivity() {

    var type = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_common)
        App.instanse = this
        type = intent?.extras?.getString("type") ?: ""
        if(type.isEmpty())
            changeView(FragmentSelectType())
        else
            changeView(FragmentFileList().apply { type = this@ActivityFile.type })
    }
}