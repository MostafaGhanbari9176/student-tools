package ir.pepotec.app.awesomeapp.view.blog

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.blog.BlogData
import ir.pepotec.app.awesomeapp.presenter.BlogPresenter
import ir.pepotec.app.awesomeapp.view.student.ability.activityAbility.ActivityAbility
import ir.pepotec.app.awesomeapp.view.uses.AF
import ir.pepotec.app.awesomeapp.view.uses.DialogProgress
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_add_blog.*
import java.io.File

class FragmentAbbBlog : MyFragment() {

    private val progress = DialogProgress()
    private var f: File? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_blog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
    }

    private fun init() {
        btnAddImgGetBlog.setOnClickListener {
            choseImage()
        }
        btnSaveMessageBlog.setOnClickListener {
            checkData()
        }
        btnDeleteImgGetBlog.setOnClickListener {
            btnAddImgGetBlog.text = "افزودن عکس"
            imgGetBlog.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.ic_image))
            f = null
        }
    }

    private fun checkData() {
        val txt = txtGetMessageBlog.text.toString().trim()
        if (txt.isEmpty()) {
            txtGetMessageBlog.apply {
                error = "لطفا پر کنید"
                requestFocus()
            }
        } else
            saveData(txt)
    }

    private fun saveData(message: String) {
        progress.show()
        BlogPresenter(object : BlogPresenter.BlogResult {
            override fun blogResult(ok: Boolean, message: String, data: ArrayList<BlogData>?) {
                progress.cancel()
                toast(message)
                if (ok)
                    (ctx as ActivityBlog).onBackPressed()

            }
        }).apply {
            if (f == null)
                add(message)
            else
                addWithImg(message, f!!)
        }
    }

    private fun choseImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        ActivityCompat.startActivityForResult(
            ctx as ActivityBlog,
            Intent.createChooser(intent, "Select Picture"),
            1,
            null
        )
    }

    fun imageData(bm: Bitmap) {
        f = AF().convertBitMapToFile(bm, ctx, "blog")
        imgGetBlog.setImageBitmap(bm)
        btnAddImgGetBlog.text = "تغییر عکس"
    }

}