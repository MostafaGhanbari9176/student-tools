package ir.pepotec.app.awesomeapp.view.student.ability

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.student.ability.activityAbility.ActivityAbility
import ir.pepotec.app.awesomeapp.view.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_ability.*

class FragmentAbility:MyFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ability, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        RVAbilityList.layoutManager = GridLayoutManager(ctx, 2)
        RVAbilityList.adapter = AdapterAbilityList(){
            showOrAddAbility(it == 0)
        }
    }

    private fun showOrAddAbility(add:Boolean) {
        val i = Intent(ctx, ActivityAbility::class.java)
        i.putExtra("add", add)
        startActivity(i)
    }

}