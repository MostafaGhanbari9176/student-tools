package ir.pepotec.app.awesomeapp.view.student

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import ir.pepotec.app.awesomeapp.view.uses.VPModel

class AdapterVPStudent(fm:FragmentManager): FragmentStatePagerAdapter(fm){
    val source = ArrayList<VPModel>()

    fun addData(data:VPModel)
    {
        source.add(data)
    }

    override fun getItem(position: Int): Fragment {
        return source.get(position).f
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return source.get(position).ttl
    }

    override fun getCount(): Int = source.size
}