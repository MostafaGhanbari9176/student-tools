package ir.pepotec.app.awesomeapp.view.student.profile.friendList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.uses.App

class AdapterFriendList (private val listener:(position:Int)->Unit): RecyclerView.Adapter<AdapterFriendList.MyHolder>() {

    class MyHolder(IV: View) : RecyclerView.ViewHolder(IV)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val v = LayoutInflater.from(App.instanse).inflate(R.layout.item_friend_list, null, false)
        return MyHolder(v)
    }

    override fun getItemCount(): Int = 15 + 1

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.itemView.apply {
            alpha = if (position == itemCount - 1) 0f else 1f
            if(position != itemCount-1)
                setOnClickListener {
                    listener(position)
                }
        }
    }
}