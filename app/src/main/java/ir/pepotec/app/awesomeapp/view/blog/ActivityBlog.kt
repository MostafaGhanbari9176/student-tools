package ir.pepotec.app.awesomeapp.view.blog

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.uses.App
import ir.pepotec.app.awesomeapp.view.uses.MyActivity

class ActivityBlog:MyActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_common)
        App.instanse = this
        changeView(FragmentBlog())
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
            (backHistory.peek() as FragmentAbbBlog).imageData(bitmap)

        }
    }

}