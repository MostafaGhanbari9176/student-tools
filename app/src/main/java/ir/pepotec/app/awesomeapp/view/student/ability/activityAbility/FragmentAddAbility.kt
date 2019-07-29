package ir.pepotec.app.awesomeapp.view.student.ability.activityAbility

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.student.ability.AbilityData
import ir.pepotec.app.awesomeapp.model.student.ability.AbilityList
import ir.pepotec.app.awesomeapp.presenter.student.AbilityPresenter
import ir.pepotec.app.awesomeapp.view.uses.DialogProgress
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_add_ability.*

class FragmentAddAbility : MyFragment(), AbilityPresenter.AbilityResult {

    var subject = ""
    var resume = ""
    var description = ""
    private var add = true
    private val progress = DialogProgress()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_ability, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        btnSaveAddAbility.setOnClickListener {
            checkData()
        }
        if (subject.length > 0) {
            add = false
            setData()
        }

    }

    private fun checkData() {
        val subject = txtSubjectAddAbility.text.toString().trim()
        if (subject.isEmpty()) {
            txtSubjectAddAbility.apply {
                requestFocus()
                setError("لطفا پر کنید")
            }
            return
        }
        val description = txtDescriptAddAbility.text.toString().trim()
        if (description.isEmpty()) {
            txtDescriptAddAbility.apply {
                requestFocus()
                setError("لطفا توضیحی درباره مهارت خود برای کارفرمایان ثبت کنید")
            }
            return
        }
        saveData(subject, description)
    }

    private fun saveData(subject: String, description: String) {
        val resume = txtResumeAddAbility.text.toString().trim()
        AbilityPresenter(this)
            .addAbility(subject, if (resume.isEmpty()) "" else resume, description)
    }

    private fun setData() {
        txtTtlAddAbility.text = "ویرایش $subject"
        txtSubjectAddAbility.setText(subject)
        txtResumeAddAbility.setText(resume)
        txtDescriptAddAbility.setText(description)
    }

    override fun abilityData(ok: Boolean, message: String, data: AbilityData?) {

    }

    override fun abilityListData(ok: Boolean, message: String, data: ArrayList<AbilityList>?) {

    }

    override fun abilityDeleteResult(ok: Boolean, message: String) {

    }

    override fun resultFromAbility(ok: Boolean, message: String) {
        progress.cancel()
        toast(message)
/*        if(ok) {
            onActivityResult()
        }*/

    }

}