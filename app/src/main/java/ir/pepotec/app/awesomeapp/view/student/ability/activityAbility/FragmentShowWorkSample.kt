package ir.pepotec.app.awesomeapp.view.student.ability.activityAbility

import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.student.ability.AbilityStatus
import ir.pepotec.app.awesomeapp.model.student.workSample.WorkSampleData
import ir.pepotec.app.awesomeapp.presenter.student.WorkSamplePresenter
import ir.pepotec.app.awesomeapp.view.uses.*
import kotlinx.android.synthetic.main.fragment_show_work_sample.*

class FragmentShowWorkSample : MyFragment() {

    private lateinit var progressInjection: ProgressInjection
    private val progress = DialogProgress()
    private lateinit var popMenu: PopupMenu
    var workSampleId = -1
    var itsMy = false
    var liked = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_show_work_sample, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressInjection = ProgressInjection(object : ProgressInjection.ProgressInjectionListener {
            override fun pressTryAgain() {
                getData()
            }
        }, ctx, view as ViewGroup, R.layout.fragment_show_work_sample)
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
        }).getWorkSample(workSampleId, itsMy)
    }

    private fun init(data: WorkSampleData) {
        if (!itsMy) {
            fabShowWorkSample.hide()
            increaseSeen()
        } else
            imgLikeShowWorkSample.visibility = View.GONE
        with(data) {
            txtSubjectShowWorkSample.text = subject
            txtDescriptionShowWorkSample.text = description
            txtAddDateWorkSample.text = add_date
            txtSeenNumShowWorkSample.text = "$seen_num"
            txtLikeNumShowWorkSample.text = "$like_num"
            this@FragmentShowWorkSample.liked = liked
            if (liked)
                imgLikeShowWorkSample.drawable.setTint(Color.RED)
            txtNewsWorkSample.text = when (status) {
                AbilityStatus.adamNamayesh -> "عدم نمایش"
                AbilityStatus.darEntezar -> "در صف انتظار"
                AbilityStatus.montasherShode -> "منتشر شده"
                AbilityStatus.delete -> "حذف شده"
                AbilityStatus.radShode -> "رد شده"
                else -> ""
            }
            downImage(img_num)
        }

        setUpPopMenu()
        fabShowWorkSample.setOnClickListener {
            popMenu.show()
        }
        imgLikeShowWorkSample.setOnClickListener {
            likeThis()
        }
    }

    private fun likeThis() {
        liked = !liked
        imgLikeShowWorkSample.drawable.setTint(if (liked) Color.RED else ContextCompat.getColor(ctx, R.color.light1))
        WorkSamplePresenter(object : WorkSamplePresenter.WorkSampleResult {

        }).likeWorkSample(workSampleId)
    }

    private fun increaseSeen() {
        WorkSamplePresenter(object : WorkSamplePresenter.WorkSampleResult {

        }).increaseSeen(workSampleId)
    }

    private fun downImage(imgNum: Int) {
        for (i in 1..imgNum) {
            WorkSamplePresenter(object : WorkSamplePresenter.WorkSampleResult {
                override fun workSampleImgData(data: ByteArray?) {
                    stImage(data, i)
                }
            }).workSampleImg("$workSampleId${i - 1}")
        }
    }

    private fun stImage(data: ByteArray?, imgNumber: Int) {
        if (data == null || data.isEmpty())
            return
        val b: Bitmap = BitmapFactory.decodeByteArray(data, 0, data.size ?: 0)
        val div: Float = b.height.toFloat() / b.width
        val w = img1ShowWorkSAmple.width
        val h = div * w
        when (imgNumber) {
            1 -> img1ShowWorkSAmple.apply {
                layoutParams.height = h.toInt()
                setImageBitmap(b)
            }
            2 -> img2ShowWorkSAmple.apply {
                CV2ShowWorkSample.visibility = View.VISIBLE
                layoutParams.height = h.toInt()
                setImageBitmap(b)
            }
            3 -> img3ShowWorkSAmple.apply {
                CV3ShowWorkSample.visibility = View.VISIBLE
                layoutParams.height = h.toInt()
                setImageBitmap(b)
            }
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
                            subject = this@FragmentShowWorkSample.txtSubjectShowWorkSample.text.toString()
                            description = this@FragmentShowWorkSample.txtDescriptionShowWorkSample.text.toString()
                        }
                        (ctx as ActivityAbility).changeView(f)
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
        WorkSamplePresenter(object : WorkSamplePresenter.WorkSampleResult {
            override fun deleteWorkSampleResult(ok: Boolean, message: String) {
                progress.cancel()
                toast(message)
            }
        }).deleteWorkSample(workSampleId)
    }

}