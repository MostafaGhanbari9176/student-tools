package ir.pepotec.app.awesomeapp.view.student.ability.activityAbility

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.uses.App

class AdapterWorkSample(private val listener:(position:Int) -> Unit):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyHolder(v: View):RecyclerView.ViewHolder(v)

    class AddHolder(v:View):RecyclerView.ViewHolder(v)

    override fun getItemViewType(position: Int): Int {
        return if(position!=0) 1 else 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == 1) MyHolder(LayoutInflater.from(App.instanse).inflate(R.layout.item_work_sample ,parent, false))
        else AddHolder(LayoutInflater.from(App.instanse).inflate(R.layout.item_add_work_sample, parent, false))
    }

    override fun getItemCount(): Int = 3

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
     holder.itemView.setOnClickListener {
         listener(position)
     }
    }
}