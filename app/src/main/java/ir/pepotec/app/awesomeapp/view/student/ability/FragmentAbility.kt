package ir.pepotec.app.awesomeapp.view.student.ability

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.student.ability.AbilityData
import ir.pepotec.app.awesomeapp.model.student.ability.AbilityList
import ir.pepotec.app.awesomeapp.presenter.student.AbilityPresenter
import ir.pepotec.app.awesomeapp.view.student.ability.activityAbility.ActivityAbility
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import ir.pepotec.app.awesomeapp.view.uses.ProgressInjection
import kotlinx.android.synthetic.main.fragment_ability.*

class FragmentAbility : MyFragment(), AbilityPresenter.AbilityResult, ProgressInjection.ProgressInjectionListener {

    lateinit var progress: ProgressInjection

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ability, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        progress = ProgressInjection(this, ctx, getView() as ViewGroup, R.layout.fragment_ability)
        getAbilityListData()
    }

    private fun getAbilityListData() {
        progress.show()
        AbilityPresenter(this).getAbilityList()
    }

    private fun setUpRV(data: ArrayList<AbilityList>) {
        RVAbilityList.layoutManager = GridLayoutManager(ctx, 2)
        RVAbilityList.adapter = AdapterAbilityList(data) {
            showOrAddAbility(it)
        }
    }

    private fun showOrAddAbility(abilityId: String) {
        val i = Intent(ctx, ActivityAbility::class.java)
        i.putExtra("abilityId", abilityId)
        startActivity(i)
    }

    override fun abilityListData(ok:Boolean, message: String, data: ArrayList<AbilityList>?) {
        if (!ok)
        //progress.error(message)
            progress.cancel()
        setUpRV(ArrayList<AbilityList>().apply { add(
            AbilityList(
                "k",
                "Android"
            )
        ) })
    }

    override fun pressTryAgain() {
        getAbilityListData()
    }

}