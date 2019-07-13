package ir.pepotec.app.awesomeapp.view.student.chat.chatContact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.view.uses.App

class AdapterChatContact(private val listener:(position:Int) -> Unit) :RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    class MyHolder(v: View):RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyHolder(LayoutInflater.from(App.instanse).inflate(R.layout.item_chat_contact, null, false))
    }

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
    {
        holder.itemView.setOnClickListener {
            listener(position)
        }
    }

}