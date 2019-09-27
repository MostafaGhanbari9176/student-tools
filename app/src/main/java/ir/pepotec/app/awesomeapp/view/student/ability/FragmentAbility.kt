package ir.pepotec.app.awesomeapp.view.student.ability

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.student.ability.AbilityList
import ir.pepotec.app.awesomeapp.presenter.student.AbilityPresenter
import ir.pepotec.app.awesomeapp.view.student.ability.activityAbility.ActivityAbility
import ir.pepotec.app.awesomeapp.view.uses.DialogProgress
import ir.pepotec.app.awesomeapp.view.uses.MyActivity
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_ability.*

class FragmentAbility : MyFragment() {

    private val progress = DialogProgress { getAbilityListData() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ability, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAbilityListData()
    }

    private fun getAbilityListData() {
        progress.show()
        AbilityPresenter(object : AbilityPresenter.AbilityResult {
            override fun abilityListData(ok: Boolean, message: String, data: ArrayList<AbilityList>?) {
                if (ok) {
                    progress.cancel()
                    setUpRV(data)
                } else {
                    progress.error(message)
                }
            }
        }).getMyList()
    }

    private fun setUpRV(data: ArrayList<AbilityList>?) {
        RVAbilityList.layoutManager = LinearLayoutManager(ctx)
        RVAbilityList.adapter = AdapterAbilityList(data!!, true) {
            showOrAddAbility(it)
        }
    }

    private fun showOrAddAbility(abilityId: Int) {
        val i = Intent(ctx, ActivityAbility::class.java)
        i.putExtra("abilityId", abilityId)
        startActivity(i)
    }

    override fun onResume() {
        super.onResume()
        getAbilityListData()
    }

}