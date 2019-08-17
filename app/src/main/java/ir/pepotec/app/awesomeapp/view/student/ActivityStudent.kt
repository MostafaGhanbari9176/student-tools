package ir.pepotec.app.awesomeapp.view.student

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.student.ability.FragmentAbility
import ir.pepotec.app.awesomeapp.view.student.chat.FragmentChat
import ir.pepotec.app.awesomeapp.view.student.profile.FragmentMyProfile
import ir.pepotec.app.awesomeapp.view.uses.App
import ir.pepotec.app.awesomeapp.view.uses.VPModel
import kotlinx.android.synthetic.main.activity_student.*
import org.jetbrains.anko.toast
import android.provider.MediaStore

class ActivityStudent : AppCompatActivity() {

    private var lastOffset = 0f
    private lateinit var fragmentMyProfile: FragmentMyProfile
    private lateinit var fragmentChat: FragmentChat
    private var imageData: Intent? = null
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
        fragmentMyProfile = FragmentMyProfile()
        adapter.addData(VPModel(fragmentMyProfile, "پروفایل"))
        fragmentChat = FragmentChat()
        adapter.addData(VPModel(fragmentChat, "گفتوگو"))
        VPStudent.adapter = adapter
        VPStudent.setCurrentItem(1)
        VPStudent.offscreenPageLimit = 3
        VPStudent.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                //changeFab(position, positionOffset)
            }

            override fun onPageSelected(position: Int) {
               // fabStudent.visibility = if (position == 2) View.VISIBLE else View.GONE
            }
        })
    }

/*    private fun changeFab(position: Int, offset: Float) {

        if (offset > lastOffset && position == 1) {
            lastOffset = offset
            fabStudent.visibility = View.VISIBLE
            fabStudent.alpha = offset
        } else if (offset < lastOffset && position != 2) {
            lastOffset = offset
            fabStudent.alpha = offset
        }
    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        App.instanse = this@ActivityStudent
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                1 -> {
                    imageData = data
                    cropImage(data?.data)
                }
                2 -> {
                    changeImage(data)
                }
            }
        } else if (imageData != null)
            changeImage(imageData)
        else
            toast("خطا, لطفا دوباره تلاش کنید")
    }

    private fun changeImage(data: Intent?) {
        val uri = data?.data
        val bitmap: Bitmap
        if (uri != null)
            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
        else {
            val extra = data?.extras
            bitmap = extra!!.getParcelable("data")
        }
        fragmentMyProfile.changeImg(bitmap)
    }

    private fun cropImage(uri: Uri?) {
        val cropIntent = Intent("com.android.camera.action.CROP", uri)
        cropIntent.apply {
            setDataAndType(uri, "image/*");
            putExtra("crop", "true")
            putExtra("outputX", 280)
            putExtra("outputY", 280)
            putExtra("aspectX", 1)
            putExtra("aspectY", 1)
            putExtra("scaleUpIfNeeded", true)
            putExtra("return-data", true)
        }
        startActivityForResult(cropIntent, 2)
    }

    override fun onResume() {
        super.onResume()
        App.instanse = this
    }

    override fun onBackPressed() {
        if (VPStudent.currentItem == 2) {
            if (fragmentChat.onBackPresed()) {
                super.onBackPressed()
                this.finish()
            }
        }
    }

}