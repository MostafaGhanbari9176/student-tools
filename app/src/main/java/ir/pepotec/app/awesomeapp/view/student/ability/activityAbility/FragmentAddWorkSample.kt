package ir.pepotec.app.awesomeapp.view.student.ability.activityAbility

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.presenter.student.WorkSamplePresenter
import ir.pepotec.app.awesomeapp.view.uses.AbsoluteFunctions
import ir.pepotec.app.awesomeapp.view.uses.DialogProgress
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_add_work_sample.*
import java.io.File

class FragmentAddWorkSample : MyFragment() {

    var subject = ""
    var description = ""
    var workSampleId = -1
    var abilityId: Int = 0
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
        if (workSampleId != -1)
            setData()

        btnChooseImgAddWorkSample.setOnClickListener {
            choseImage(imgList.size + 1)
        }
        btnSaveWorkSample.setOnClickListener {
            checkData()
        }
        btnDeleteAddWorkSample2.setOnClickListener {
            imgList.remove(imgList.get(1))
            LLAddWorkSample2.visibility = View.GONE
            btnChooseImgAddWorkSample.visibility = View.VISIBLE
        }
        btnDeleteAddWorkSample3.setOnClickListener {
            imgList.remove(imgList.get(if (imgList.size == 3) 2 else 1))
            LLAddWorkSample3.visibility = View.GONE
            btnChooseImgAddWorkSample.visibility = View.VISIBLE
        }
        btnAddWorkSample1.setOnClickListener {
            imgList.remove(imgList.get(0))
            choseImage(1)
        }
        btnAddWorkSample2.setOnClickListener {
            imgList.remove(imgList.get(1))
            choseImage(2)
        }
        btnAddWorkSample3.setOnClickListener {
            imgList.remove(imgList.get(2))
            choseImage(3)
        }

    }

    private fun choseImage(imgNumber: Int) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        ActivityCompat.startActivityForResult(
            ctx as ActivityAbility,
            Intent.createChooser(intent, "Select Picture"),
            imgNumber,
            null
        )
    }

    fun image1(b: Bitmap) {
        val file = AbsoluteFunctions().convertBitMapToFile(b, ctx, "image1")
        imgList.add(file)
        btnChooseImgAddWorkSample.text = "انتخاب عکس دیگر (الزامی نیست)"
        AbsoluteFunctions().setImage(imgAddWorkSample1, file)
        LLAddWorkSample1.visibility = View.VISIBLE
    }

    fun image2(b: Bitmap) {
        val file = AbsoluteFunctions().convertBitMapToFile(b, ctx, "image2")
        imgList.add(file)
        AbsoluteFunctions().setImage(imgAddWorkSample2, file)
        LLAddWorkSample2.visibility = View.VISIBLE
    }

    fun image3(b: Bitmap) {
        val file = AbsoluteFunctions().convertBitMapToFile(b, ctx, "image3")
        imgList.add(file)
        btnChooseImgAddWorkSample.visibility = View.GONE
        AbsoluteFunctions().setImage(imgAddWorkSample3, file)
        LLAddWorkSample3.visibility = View.VISIBLE
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
        if (workSampleId == -1) {
            WorkSamplePresenter(object : WorkSamplePresenter.WorkSampleResult {
                override fun addWorkSampleResult(ok: Boolean, message: String) {
                    progress.cancel()
                    toast(message)
                    if (ok)
                        (ctx as ActivityAbility).onBackPressed()
                }
            }).addWorkSample(abilityId, subject, description, imgList)
        } else {
            WorkSamplePresenter(object : WorkSamplePresenter.WorkSampleResult {
                override fun editWorkSampleResult(ok: Boolean, message: String) {
                    progress.cancel()
                    toast(message)
                    if (ok)
                        (ctx as ActivityAbility).onBackPressed()
                }
            }).editWorkSample(workSampleId, subject, description, imgList)
        }
    }

    private fun setData() {
        txtSubjectAddWorkSample.setText(subject)
        txtdescriptAddWorkSample.setText(description)
        txtTtlAddWorkSample.text = "ویرایش $subject"
    }
}