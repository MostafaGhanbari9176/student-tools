package ir.pepotec.app.awesomeapp.view.student.ability.activityAbility

import android.content.ClipDescription
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.presenter.student.StudentProfilePresenter
import ir.pepotec.app.awesomeapp.presenter.student.WorkSamplePresenter
import ir.pepotec.app.awesomeapp.view.student.ActivityStudent
import ir.pepotec.app.awesomeapp.view.uses.AbsoluteFunctions
import ir.pepotec.app.awesomeapp.view.uses.DialogProgress
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_add_work_sample.*
import java.io.File
import javax.security.auth.Subject

class FragmentAddWorkSample : MyFragment() {

    var subject = ""
    var description = ""
    var workSampleId = ""

    private val imgList = ArrayList<File>()
    private val progress = DialogProgress()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_work_sample, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        if (!workSampleId.isEmpty())
            setData()

        btnChooseImgAddWorkSample.setOnClickListener {
            choseImage()
        }
        btnSaveWorkSample.setOnClickListener {
            checkData()
        }
        btnDeleteAddWorkSample2.setOnClickListener {
            imgList.remove(imgList.get(1))
            LLAddWorkSample2.visibility = View.GONE
        }
        btnDeleteAddWorkSample3.setOnClickListener {
            imgList.remove(imgList.get(2))
            LLAddWorkSample3.visibility = View.GONE
        }
    }

    private fun choseImage() {
        val pickPhoto = Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(pickPhoto, imgList.size + 1)
    }

    fun image1(b: Bitmap) {
        val file = AbsoluteFunctions().convertBitMapToFile(b, ctx)
        imgList.add(file)
        btnChooseImgAddWorkSample.text = "انتخاب عکس دیگر (الزامی نیست)"
        setImage(file, imgAddWorkSample1)
        LLAddWorkSample1.visibility = View.VISIBLE
    }

    fun image2(b: Bitmap) {
        val file = AbsoluteFunctions().convertBitMapToFile(b, ctx)
        imgList.add(file)
        setImage(file, imgAddWorkSample2)
        LLAddWorkSample2.visibility = View.VISIBLE
    }

    fun image3(b: Bitmap) {
        val file = AbsoluteFunctions().convertBitMapToFile(b, ctx)
        imgList.add(file)
        btnChooseImgAddWorkSample.visibility = View.GONE
        setImage(file, imgAddWorkSample3)
        LLAddWorkSample3.visibility = View.VISIBLE
    }

    private fun setImage(f: File, v: ImageView) {
        Glide.with(ctx)
            .load(f)
            .into(v)
    }

    private fun checkData() {
        val subject = txtSubjectAddWorkSample.text.toString().trim()
        if (subject.isEmpty()) {
            txtSubjectAddWorkSample.apply {
                error = "لطفا پر کنید"
                requestFocus()
            }
            return
        }

        val description = txtdescriptAddWorkSample.text.toString().trim()
        if (subject.isEmpty()) {
            txtdescriptAddWorkSample.apply {
                error = "لطفا توضیحی درباره نمونه کار خود بنویسید"
                requestFocus()
            }
            return
        }

        if (imgList.size == 0) {
            btnChooseImgAddWorkSample.apply {
                error = "انتخاب عکس کاور الزامی است"
                requestFocus()
            }
            return
        }

        saveData(subject, description)
    }

    private fun saveData(subject: String, description: String) {
        progress.show()
        if (workSampleId.isEmpty())
            WorkSamplePresenter(object : WorkSamplePresenter.WorkSampleResult {
                override fun addWorkSampleResult(ok: Boolean, message: String) {
                    progress.cancel()
                    toast(message)
                    if (ok)
                        (ctx as ActivityAbility).onBackPressed()
                }
            }).addWorkSample(subject, description, imgList)
        else
            WorkSamplePresenter(object : WorkSamplePresenter.WorkSampleResult {
                override fun editWorkSampleResult(ok: Boolean, message: String) {
                    progress.cancel()
                    toast(message)
                    if (ok)
                        (ctx as ActivityAbility).onBackPressed()
                }
            }).editWorkSample(workSampleId, subject, description, imgList)
    }

    private fun setData() {
        txtSubjectAddWorkSample.setText(subject)
        txtdescriptAddWorkSample.setText(description)
        txtTtlAddWorkSample.text = "ویرایش $subject"
    }
}