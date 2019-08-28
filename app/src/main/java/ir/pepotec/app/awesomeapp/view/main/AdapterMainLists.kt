package ir.pepotec.app.awesomeapp.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.uses.App
import kotlinx.android.synthetic.main.item_main_lists.view.*

class AdapterMainLists(private val data:ArrayList<MainListsModel>, val listener: (position: Int) -> Unit) : RecyclerView.Adapter<AdapterMainLists.Holder>() {

    private val source = ArrayList<String>()

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(App.instanse).inflate(
                R.layout.item_main_lists,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.apply {
            txtSubIHome.text = data[position].sub
            fabHomeList.setImageDrawable(ContextCompat.getDrawable(App.instanse, data[position].imgId))
            fabHomeList.setOnClickListener {
                listener(position)
            }
            setOnClickListener {
                listener(position)
            }
        }
    }

}