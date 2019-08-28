package ir.pepotec.app.awesomeapp.view.launcher

import android.content.Intent
import android.os.Bundle
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.student.profile.StudentProfileDb
import ir.pepotec.app.awesomeapp.view.account.ActivityAccount
import ir.pepotec.app.awesomeapp.view.main.ActivityMain
import ir.pepotec.app.awesomeapp.view.uses.MyActivity

class ActivityLauncher:MyActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_common)
        startActivity(Intent(this, if(StudentProfileDb().isLogIn) ActivityMain::class.java else ActivityAccount::class.java))
        this.finish()
    }
}