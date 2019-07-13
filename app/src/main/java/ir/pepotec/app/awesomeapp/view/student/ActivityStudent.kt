package ir.pepotec.app.awesomeapp.view.student

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.student.ability.FragmentAbility
import ir.pepotec.app.awesomeapp.view.student.chat.FragmentChat
import ir.pepotec.app.awesomeapp.view.student.profile.FragmentProfile
import ir.pepotec.app.awesomeapp.view.uses.App
import ir.pepotec.app.awesomeapp.view.uses.VPModel
import kotlinx.android.synthetic.main.activity_student.*
import org.jetbrains.anko.toast

class ActivityStudent : AppCompatActivity() {

    private var lastOffset = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
        App.instanse = this
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        fabStudent.setOnClickListener {
            toast("clicked")
        }
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
        VPStudent.offscreenPageLimit = 3
        VPStudent.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                changeFab(position, positionOffset)
            }

            override fun onPageSelected(position: Int) {
                fabStudent.visibility = if (position == 2) View.VISIBLE else View.GONE
            }
        })
    }

    private fun changeFab(position: Int, offset: Float) {

        if (offset > lastOffset && position == 1) {
            lastOffset = offset
            fabStudent.visibility = View.VISIBLE
            fabStudent.alpha = offset
        } else if (offset < lastOffset && position != 2) {
            lastOffset = offset
            fabStudent.alpha = offset
        }
    }

    override fun onResume() {
        super.onResume()
        App.instanse = this
    }
}