package ir.pepotec.app.awesomeapp.view.student.ability

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.student.ability.AbilityList
import ir.pepotec.app.awesomeapp.view.uses.App
import kotlinx.android.synthetic.main.item_ability.view.*

class AdapterAbilityList(
    private val data: ArrayList<AbilityList>,
    private val itsMy: Boolean,
    private val listener: (abilityId: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 2 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) MyHolder(
            LayoutInflater.from(App.instanse).inflate(
                R.layout.item_ability,
                parent,
                false
            )
        )
        else AddHolder(LayoutInflater.from(App.instanse).inflate(R.layout.item_ability, parent, false))
    }

    override fun getItemCount(): Int = data.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.fabAbility.setOnClickListener {
            listener(if (position == 0) -1 else data.get(position - 1).ability_id)
        }
        if (position == 0) {
            (holder as AddHolder).onBind(itsMy)
            return
        }
        (holder as MyHolder).itemView.apply {
            fabAbility.text = data.get(position - 1).subject
        }

    }

    class MyHolder(v: View) : RecyclerView.ViewHolder(v)
    class AddHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun onBind(itsMy: Boolean) {
            itemView.fabAbility.apply {
                if (itsMy) {
                    text = "افزودن مهارت"
                    icon = ContextCompat.getDrawable(App.instanse, R.drawable.ic_add)
                    setBackgroundColor(Color.WHITE)
                } else
                    visibility = View.GONE
            }
        }
    }
}