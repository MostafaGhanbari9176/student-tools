package ir.pepotec.app.awesomeapp.view.student

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.student.ability.FragmentAbility
import ir.pepotec.app.awesomeapp.view.student.chat.FragmentChat
import ir.pepotec.app.awesomeapp.view.student.profile.FragmentProfile
import ir.pepotec.app.awesomeapp.view.uses.App
import ir.pepotec.app.awesomeapp.view.uses.VPModel
import kotlinx.android.synthetic.main.activity_student.*

class ActivityStudent:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
        App.instanse = this
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        initVP()
    }

    private fun initVP() {
        tabLayoutStudent.setupWithViewPager(VPStudent)
        val adapter = AdapterVPStudent(supportFragmentManager)
        adapter.addData(VPModel(FragmentAbility(), "مهارت ها"))
        adapter.addData(VPModel(FragmentProfile(), "پروفایل"))
        adapter.addData(VPModel(FragmentChat(), "گفتوگو"))
        VPStudent.adapter = adapter
        VPStudent.setCurrentItem(1)
    }

    override fun onResume() {
        super.onResume()
        App.instanse = this
    }
}