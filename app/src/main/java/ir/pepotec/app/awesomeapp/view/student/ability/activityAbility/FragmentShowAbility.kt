package ir.pepotec.app.awesomeapp.view.student.ability.activityAbility

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.student.ability.AbilityData
import ir.pepotec.app.awesomeapp.model.student.ability.AbilityList
import ir.pepotec.app.awesomeapp.model.student.workSample.workSampleList
import ir.pepotec.app.awesomeapp.presenter.student.AbilityPresenter
import ir.pepotec.app.awesomeapp.presenter.student.WorkSamplePresenter
import ir.pepotec.app.awesomeapp.view.uses.DialogProgress
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import ir.pepotec.app.awesomeapp.view.uses.ProgressInjection
import kotlinx.android.synthetic.main.fragment_show_ability.*

class FragmentShowAbility : MyFragment(), AbilityPresenter.AbilityResult,ProgressInjection.ProgressInjectionListener {

    private lateinit var popMenu: PopupMenu
    var abilityId = ""
    private lateinit var  progress:ProgressInjection
    private val dialogProgress = DialogProgress()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_show_ability, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progress = ProgressInjection(this, ctx, view as ViewGroup, R.layout.fragment_show_ability)
        getAbilityData()
    }

    private fun init(data: AbilityData?) {
        txtSubjectShowAbility.text = data?.subject
        txtResumeShowAbility.text = data?.resume
        txtDescriptionShowAbility.text = data?.description
        setUpPopMenu()
        fabShowAbility.setOnClickListener {
            popMenu.show()
        }
        getWorkSampleData()
    }

    private fun getWorkSampleData() {
        WorkSamplePresenter(object :WorkSamplePresenter.WorkSampleResult{
            override fun workSampleListData(ok: Boolean, message: String, data: ArrayList<workSampleList>?) {
                pBarWorkSampleList.visibility = View.GONE
                if(ok)
                    setUpRV(data)
                else
                    txtErrorWorkSampleList.apply {
                        visibility = View.VISIBLE
                        text = message
                    }

            }
        }).workSampleList(abilityId)
    }



    private fun getAbilityData() {
       progress.show()
        AbilityPresenter(this).getAbility(abilityId)
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
                            subject = txtSubjectShowAbility.text.toString()
                            description = txtDescriptionShowAbility.text.toString()
                            resume = txtResumeShowAbility.text.toString()
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
            .setTitle("حذف مهارت")
            .setMessage("مهارت شما همراه با نمونه کارهای آن حذف می شود")
            .setPositiveButton("انجام بده", DialogInterface.OnClickListener {
                    dialog, which -> deleteAbility()
            })
            .setNegativeButton("لغو", DialogInterface.OnClickListener {
                    dialog, which -> dialog.cancel()
            })
            .show()
    }

    private fun deleteAbility() {
        dialogProgress.show()
        AbilityPresenter(this).deleteAbility(abilityId)
    }

    private fun showHideDialog() {
        MaterialAlertDialogBuilder(ctx)
            .setTitle("عدم نمایش مهارت")
            .setMessage("با این کار کاربران مهارت شما را نمی بینند")
            .setPositiveButton("انجام بده", DialogInterface.OnClickListener {
                    dialog, which -> hideAbility()
            })
            .setNegativeButton("لغو", DialogInterface.OnClickListener {
                    dialog, which -> dialog.cancel()
            })
            .show()
    }

    private fun hideAbility() {
        dialogProgress.show()
        AbilityPresenter(this).eyeCloseAbility(abilityId)
    }

    private fun showOrAddWorkSample(workSampleId:String) {
        if(workSampleId.isEmpty())
        (ctx as ActivityAbility).changeView(FragmentAddWorkSample())
        else
        {
            val f = FragmentShowWorkSample()
            f.workSampleId = workSampleId
            (ctx as ActivityAbility).changeView(f)
        }
    }

    private fun setUpRV(data: ArrayList<workSampleList>?) {
        RVWorkSample.visibility = View.VISIBLE
        RVWorkSample.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
        RVWorkSample.adapter = AdapterWorkSample(data!!) {
            showOrAddWorkSample(it )
        }
    }

    override fun abilityData(ok:Boolean, message:String, data: AbilityData?) {
        if(!ok)
        {
            progress.error(message)
            return
        }
        progress.cancel()
        init(data)
    }

    override fun abilityListData(ok:Boolean, message:String, data: ArrayList<AbilityList>?) {
        
    }

    override fun abilityDeleteResult(ok: Boolean, message: String) {
        dialogProgress.cancel()
        toast(message)
    }

    override fun resultFromAbility(ok: Boolean, message: String) {
        dialogProgress.cancel()
        toast(message)
    }

    override fun pressTryAgain() {
     getAbilityData()
    }

}