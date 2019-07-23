package ir.pepotec.app.awesomeapp.view.student.ability.activityAbility

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_add_ability.*

class FragmentAddAbility :MyFragment(){

    var subject = ""
    var resume = ""
    var description = ""
    private var add = true
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_ability, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        if(subject.length >0 )
        {
            add = false
            setData()
        }
    }

    private fun setData() {
        txtTtlAddAbility.text = "ویرایش $subject"
        txtSubjectAddAbility.setText(subject)
        txtResumeAddAbility.setText(resume)
        txtDescriptAddAbility.setText(description)
    }
}