package ir.pepotec.app.awesomeapp.view.student.ability.activityAbility

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.uses.App
import ir.pepotec.app.awesomeapp.view.uses.MyActivity

class ActivityAbility : MyActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_common)
        App.instanse = this
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        val abilityId = intent?.extras?.getInt("abilityId")
        changeView(if (abilityId == -1) FragmentAddAbility() else FragmentShowAbility().apply { this.abilityId = abilityId ?: -1; this.itsMy = true})
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK)
        {
            val uri = data?.data
            val bitmap: Bitmap
            if (uri != null)
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            else {
                val extra = data?.extras
                bitmap = extra!!.getParcelable("data")
            }
            when(requestCode)
            {
                1 -> (backHistory.peek() as FragmentAddWorkSample).image1(bitmap)
                2 -> (backHistory.peek() as FragmentAddWorkSample).image2(bitmap)
                3 -> (backHistory.peek() as FragmentAddWorkSample).image3(bitmap)
            }
        }
    }

}