package ir.pepotec.app.awesomeapp.view.student

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.student.ability.FragmentAbility
import ir.pepotec.app.awesomeapp.view.student.profile.FragmentMyProfile
import ir.pepotec.app.awesomeapp.view.uses.App
import org.jetbrains.anko.toast
import android.provider.MediaStore
import ir.pepotec.app.awesomeapp.view.uses.MyActivity

class ActivityStudent : MyActivity() {

    private var imageData: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_common)
        App.instanse = this
        changeView(
            when (intent?.extras?.getString("show")) {
                "ability" -> FragmentAbility()
                else -> FragmentMyProfile()

            }
        )
    }

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
            bitmap = extra!!.getParcelable("data")!!
        }
        (backHistory.peek() as FragmentMyProfile).changeImg(bitmap)
    }

    private fun cropImage(uri: Uri?) {
        val cropIntent = Intent("com.android.camera.action.CROP", uri)
        cropIntent.apply {
            setDataAndType(uri, "image/*");
            putExtra("crop", "true")
            putExtra("outputX", 512)
            putExtra("outputY", 512)
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
        super.onBackPressed()
        this.finish()
    }

}