package ir.pepotec.app.awesomeapp.view.main.search.searchAbility

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.student.ability.AbilityData
import ir.pepotec.app.awesomeapp.view.uses.App
import kotlinx.android.synthetic.main.item_search_ability.view.*

class AdapterAbilitySearch(val data:ArrayList<AbilityData>, private val listener:(ability:AbilityData) -> Unit, private val reachesBottom:() -> Unit):RecyclerView.Adapter<AdapterAbilitySearch.MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(App.instanse).inflate(R.layout.item_search_ability, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.itemView.apply {
            txtSubjectSearchAbility.text = data[position].subject
            txtDescriptionSearcAbility.text = data[position].description
            setOnClickListener {
                listener(data[position])
            }
        }
        if(position == itemCount -1)
            reachesBottom()
    }

    class MyHolder(v: View):RecyclerView.ViewHolder(v)

}