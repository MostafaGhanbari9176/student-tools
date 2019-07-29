package ir.pepotec.app.awesomeapp.view.student.ability.activityAbility

import android.content.DialogInterface
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.student.workSample.WorkSampleData
import ir.pepotec.app.awesomeapp.presenter.student.WorkSamplePresenter
import ir.pepotec.app.awesomeapp.view.uses.DialogProgress
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import ir.pepotec.app.awesomeapp.view.uses.ProgressInjection
import kotlinx.android.synthetic.main.fragment_show_work_sample.*

class FragmentShowWorkSample : MyFragment() {

    private lateinit var progressInjection: ProgressInjection
    private val progress = DialogProgress()
    private lateinit var popMenu: PopupMenu
    var workSampleId = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_show_work_sample, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }

    private fun getData() {
        progressInjection.show()
        WorkSamplePresenter(object : WorkSamplePresenter.WorkSampleResult {
            override fun workSampleData(ok: Boolean, message: String, data: WorkSampleData?) {
                if (ok) {
                    progressInjection.cancel()
                    init(data!!)
                } else
                    progressInjection.error(message)
            }
        }).getWorkSample(workSampleId)
    }

    private fun init(data: WorkSampleData) {
        txtSubjectShowWorkSample.text = data.subject
        txtDescriptionShowWorkSample.text = data.description
        downImage(data.imgId)
        setUpPopMenu()
        fabShowWorkSample.setOnClickListener {
            popMenu.show()
        }
    }

    private fun downImage(data: ArrayList<String>) {
        for (o in data) {
            WorkSamplePresenter(object : WorkSamplePresenter.WorkSampleResult {
                override fun workSampleImgData(data: ByteArray?) {
                    showImage(data)
                }
            }).workSampleImg(o)
        }
    }

    private fun showImage(data: ByteArray?) {
        val img = addImageView()
        Glide.with(ctx)
            .load(data)
            .into(img)
    }

    private fun addImageView(): ImageView {
        val point = Point()
        (ctx as ActivityAbility).windowManager.defaultDisplay.getRealSize(point)
        val w = (point.x * 0.75).toInt()
        val param = LinearLayout.LayoutParams(w.toInt(), w * 16/9)
        param.topMargin = 16
        return ImageView(ctx).apply {
            layoutParams = param
            LLParentWorkSampleImages.addView(this)
        }
    }

    private fun setUpPopMenu() {
        popMenu = PopupMenu(ctx, fabShowWorkSample)
        popMenu.apply {
            menuInflater.inflate(R.menu.menu_ability, menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menuAbilityEdit -> {
                        val f = FragmentAddWorkSample()
                        f.apply {
                            workSampleId = this@FragmentShowWorkSample.workSampleId
                            subject = txtSubjectShowWorkSample.text.toString()
                            description = txtDescriptionShowWorkSample.text.toString()
                        }
                        (ctx as ActivityAbility).changeView(f)
                    }

                    R.id.menuAbilityHide -> {
                        showHideDialog()
                    }
                    R.id.menuAbilityDelete -> {
                        showDeleteDialog()
                    }
                }
                true
            }
        }
    }

    private fun showDeleteDialog() {
        MaterialAlertDialogBuilder(ctx)
            .setTitle("حذف نمونه کار؟")
            .setPositiveButton("انجام بده", DialogInterface.OnClickListener { dialog, which -> delete() })
            .setNegativeButton("لغو", DialogInterface.OnClickListener { dialog, which -> toast("cancel") })
            .show()
    }

    private fun delete() {
        progress.show()
        WorkSamplePresenter(object :WorkSamplePresenter.WorkSampleResult{
            override fun deleteWorkSampleResult(ok: Boolean, message: String) {
                progress.cancel()
                    toast(message)
            }
            override fun workSampleError(message: String) {
                progress.cancel()
            }
        }).deleteWorkSample(workSampleId)
    }

    private fun showHideDialog() {
        MaterialAlertDialogBuilder(ctx)
            .setTitle("عدم نمایش نمونه کار")
            .setMessage("با این کار کاربران نمونه کار شما را نمی بینند")
            .setPositiveButton("انجام بده", DialogInterface.OnClickListener { dialog, which -> hide() })
            .setNegativeButton("لغو", DialogInterface.OnClickListener { dialog, which -> toast("cancel") })
            .show()
    }

    private fun hide() {
        progress.show()
        WorkSamplePresenter(object :WorkSamplePresenter.WorkSampleResult{
            override fun eyeCloseWorkSampleResult(ok: Boolean, message: String) {
                progress.cancel()
                toast(message)
            }

            override fun workSampleError(message: String) {
                progress.cancel()
            }
        }).eyeCloseWorkSample(workSampleId)
    }

}