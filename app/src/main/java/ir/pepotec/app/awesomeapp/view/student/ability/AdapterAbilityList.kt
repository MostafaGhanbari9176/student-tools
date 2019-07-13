package ir.pepotec.app.awesomeapp.view.student.ability

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.uses.App
import kotlinx.android.synthetic.main.item_ability.view.*

class AdapterAbilityList : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 2 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) MyHolder(
            LayoutInflater.from(App.instanse).inflate(
                R.layout.item_ability,
                null,
                false
            )
        )
        else AddHolder(LayoutInflater.from(App.instanse).inflate(R.layout.item_ability, null, false))
    }

    override fun getItemCount(): Int = 5 + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == 0) {
            (holder as AddHolder).onBind()
            return
        }

    }

    class MyHolder(v: View) : RecyclerView.ViewHolder(v)
    class AddHolder(v: View) : RecyclerView.ViewHolder(v)
    {
        fun onBind()
        {
            itemView.efabAbility.apply {
                text = "افزودن"
                icon = ContextCompat.getDrawable(App.instanse, R.drawable.ic_add)
                setBackgroundColor(Color.WHITE)

            }
        }
    }
}