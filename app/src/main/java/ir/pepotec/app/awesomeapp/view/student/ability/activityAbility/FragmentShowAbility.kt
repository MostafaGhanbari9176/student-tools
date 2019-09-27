package ir.pepotec.app.awesomeapp.view.student.ability.activityAbility

import android.animation.ObjectAnimator
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.student.ability.AbilityData
import ir.pepotec.app.awesomeapp.model.student.ability.AbilityStatus
import ir.pepotec.app.awesomeapp.model.student.workSample.workSampleList
import ir.pepotec.app.awesomeapp.presenter.student.AbilityPresenter
import ir.pepotec.app.awesomeapp.presenter.student.WorkSamplePresenter
import ir.pepotec.app.awesomeapp.view.student.profile.activityProfile.ActivityProfile
import ir.pepotec.app.awesomeapp.view.uses.DialogProgress
import ir.pepotec.app.awesomeapp.view.uses.MyActivity
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_show_ability.*

class FragmentShowAbility : MyFragment(), AbilityPresenter.AbilityResult {

    private lateinit var popMenu: PopupMenu
    var abilityId = -1
    var itsMy = false
    var user_id = -1
    private val progress = DialogProgress { getAbilityData() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_show_ability, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAbilityData()
    }

    private fun init(data: AbilityData?) {
        if (!itsMy) {
            increaseSeen()
            fabShowAbility.hide()
            if (user_id != -1)
                setUpFabShowProfile()
        }
        setUpPopMenu()
        txtSubjectShowAbility.text = data?.subject
        txtResumeShowAbility.text = if (data?.resume.isNullOrEmpty()) "هیچ سابقه کاری ثبت نشده است!" else data?.resume
        txtDescriptionShowAbility.text = data?.description
        txtDateShowAbility.text = data?.add_date
        txtSeenNumShowAbility.text = data?.seen_num.toString()
        txtNewsShowAbility.text = when (data?.status) {
            AbilityStatus.adamNamayesh -> "عدم نمایش"
            AbilityStatus.darEntezar -> "در صف انتظار"
            AbilityStatus.montasherShode -> "منتشر شده"
            AbilityStatus.delete -> "حذف شده"
            AbilityStatus.radShode -> "رد شده"
            else -> ""
        }
        fabShowAbility.setOnClickListener {
            popMenu.show()
        }
        getWorkSampleData()
    }

    private fun setUpFabShowProfile() {
        ObjectAnimator.ofFloat(fabShowProfile, View.TRANSLATION_Y, 0f, fabShowProfile.height / 2f).apply {
            duration = 300
            start()
        }
        ObjectAnimator.ofFloat(fabShowProfile, View.ALPHA, 0f, 1f).apply {
            duration = 150
            start()
        }
        fabShowProfile.setOnClickListener {
            showProfile()
        }
    }

    private fun showProfile() {
        startActivity(Intent(ctx, ActivityProfile::class.java).apply { putExtra("userId", user_id) })
    }

    private fun increaseSeen() {
        AbilityPresenter(object : AbilityPresenter.AbilityResult {

        }).increaseSeen(abilityId)
    }

    private fun getWorkSampleData() {
        WorkSamplePresenter(object : WorkSamplePresenter.WorkSampleResult {
            override fun workSampleListData(ok: Boolean, message: String, data: ArrayList<workSampleList>?) {
                pBarWorkSampleList.visibility = View.GONE
                if (ok)
                    setUpRV(data)
                else
                    txtErrorWorkSampleList.apply {
                        visibility = View.VISIBLE
                        text = message
                    }

            }
        }).workSampleList(abilityId, itsMy)
    }


    private fun getAbilityData() {
        progress.show()
        AbilityPresenter(this).getSingle(abilityId, itsMy)
    }

    private fun setUpPopMenu() {
        popMenu = PopupMenu(ctx, fabShowAbility)
        popMenu.apply {
            menuInflater.inflate(R.menu.menu_ability, menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menuAbilityEdit -> {
                        val f = FragmentAddAbility()
                        f.apply {
                            abilityId = this@FragmentShowAbility.abilityId
                            subject = this@FragmentShowAbility.txtSubjectShowAbility.text.toString()
                            description = this@FragmentShowAbility.txtDescriptionShowAbility.text.toString()
                            resume = this@FragmentShowAbility.txtResumeShowAbility.text.toString()
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
            .setTitle("حذف مهارت")
            .setMessage("مهارت شما همراه با نمونه کارهای آن حذف می شود")
            .setPositiveButton("انجام بده", DialogInterface.OnClickListener { dialog, which ->
                deleteAbility()
            })
            .setNegativeButton("لغو", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
            .show()
    }

    private fun deleteAbility() {
        progress.show()
        AbilityPresenter(this).deleteAbility(abilityId)
    }

    private fun showOrAddWorkSample(workSampleId: Int) {
        if (workSampleId == -1)
            (ctx as ActivityAbility).changeView(FragmentAddWorkSample().apply {
                this.abilityId = this@FragmentShowAbility.abilityId
            })
        else {
            val f = FragmentShowWorkSample()
            f.workSampleId = workSampleId
            f.itsMy = itsMy
            (ctx as MyActivity).changeView(f)
        }
    }

    private fun setUpRV(data: ArrayList<workSampleList>?) {
        txtWorkSampleCounter.text = "${data?.size ?: 0}/2";
        RVWorkSample.visibility = View.VISIBLE
        RVWorkSample.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
        RVWorkSample.adapter = AdapterWorkSample(data!!, itsMy) {
            showOrAddWorkSample(it)
        }
    }

    override fun abilityData(ok: Boolean, message: String, data: AbilityData?) {
        if (ok) {
            progress.cancel()
            init(data)
        } else {
            progress.error(message)
        }
    }

    override fun resultFromAbility(ok: Boolean, message: String) {
        progress.cancel()
        toast(message)
    }


}